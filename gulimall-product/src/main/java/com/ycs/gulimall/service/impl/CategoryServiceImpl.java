package com.ycs.gulimall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycs.gulimall.dao.CategoryDao;
import com.ycs.gulimall.entity.CategoryBrandRelationEntity;
import com.ycs.gulimall.entity.CategoryEntity;
import com.ycs.gulimall.service.CategoryBrandRelationService;
import com.ycs.gulimall.service.CategoryService;
import com.ycs.gulimall.utils.PageUtils;
import com.ycs.gulimall.utils.Query;
import com.ycs.gulimall.vo.Catelog2Vo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    private Map<String,Object> cache = new HashMap<>();
    // @Resource
    // private CategoryDao categoryDao;
    @Resource
    private CategoryBrandRelationService categoryBrandRelationService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedissonClient redissonClient;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {

        //1、查询出所有分类
        List<CategoryEntity> entities = super.baseMapper.selectList(null);

        //2、组装成父子的树形结构

        //2.1)、找到所有一级分类
        List<CategoryEntity> levelMenus = entities.stream()
                .filter(e -> e.getParentCid() == 0)
                .map((menu) -> {
                    menu.setChildren(getChildrens(menu, entities));
                    return menu;
                })
                .sorted((menu, menu2) -> {
                    return (menu.getSort() == null ? 0 : menu.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
                })
                .collect(Collectors.toList());

        return levelMenus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 检查当前删除的菜单，是否被别的地方引用
        List<CategoryBrandRelationEntity> categoryBrandRelation =
                categoryBrandRelationService.list(new QueryWrapper<CategoryBrandRelationEntity>().in("catelog_id", asList));

        if (categoryBrandRelation.size() == 0) {
            //逻辑删除
            baseMapper.deleteBatchIds(asList);
        } else {
            throw new RuntimeException("该菜单下面还有属性，无法删除!");
        }
    }


    //递归查找所有菜单的子菜单
    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all) {

        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid().equals(root.getCatId());
        }).map(categoryEntity -> {
            //1、找到子菜单(递归)
            categoryEntity.setChildren(getChildrens(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu, menu2) -> {
            //2、菜单的排序
            return (menu.getSort() == null ? 0 : menu.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return children;

    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {

        List<Long> paths = new ArrayList<>();

        //递归查询是否还有父节点
        List<Long> parentPath = findParentPath(catelogId, paths);

        //进行一个逆序排列
        Collections.reverse(parentPath);

        return (Long[]) parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 每一个需要缓存的数据我们都来指定要放到那个名字的缓存【缓存的分区(按照业务类型分)】
     * 代表当前方法的结果需要缓存,如果缓存中有方法都不用调用,如果缓存中没有会调用方法.最后将方法的结果放入缓存
     * 默认行为
     *      如果缓存中有,方法不再调用
     *      key是默认生成的:缓存的名字::SimpleKey::[](自动生成key值)
     *      缓存的value值，默认使用jdk序列化机制，将序列化的数据存到redis中
     *      默认时间是 -1：
     *
     * 自定义操作：key的生成
     *      指定生成缓存的key：key属性指定，接收一个Spel
     *      指定缓存的数据的存活时间:配置文档中修改存活时间
     *      将数据保存为json格式
     *
     * 4、Spring-Cache的不足之处：
     *  1）、读模式
     *      缓存穿透：查询一个null数据.解决方案：缓存空数据
     *      缓存击穿：大量并发进来同时查询一个正好过期的数据.解决方案：加锁 ? 默认是无加锁的;使用sync = true来解决击穿问题
     *      缓存雪崩：大量的key同时过期.解决：加随机过期时间
     *  2)、写模式：（缓存与数据库一致）
     *      1）、读写加锁
     *      2）、引入Canal,感知到MySQL的更新去更新Redis
     *      3）、读多写多，直接去数据库查询就行
     *
     *  总结：
     *      常规数据（读多写少,即时性&一致性要求不高的数据,完全可以使用Spring-Cache）：写模式(只要缓存的数据有过期时间就足够了)
     *      特殊数据：特殊设计
     *
     *  原理：
     *      CacheManager(RedisCacheManager)->Cache(RedisCache)->Cache负责缓存的读写
     * @return
     */
    @Cacheable(value = {"category"}, key = "#root.method.name", sync = true)
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        log.info("获取商城商品一级目录开始．．．");
        long start = System.currentTimeMillis();
        List<CategoryEntity> categoryEntities = this.baseMapper.selectList(
                new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        log.info("获取商城商品一级目录消耗时间(单位毫秒)：{}", (System.currentTimeMillis() - start));
        return categoryEntities;
    }

    /**
     * 级联更新所有关联的数据
     * @CacheEvict:失效模式
     * @CachePut:双写模式，需要有返回值
     * 1、同时进行多种缓存操作：@Caching
     * 2、指定删除某个分区下的所有数据 @CacheEvict(value = "category",allEntries = true)
     * 3、存储同一类型的数据，都可以指定为同一分区
     * @param category
     */
    // @Caching(evict = {
    //         @CacheEvict(value = "category", key = "'getLevel1Categorys'"),
    //         @CacheEvict(value = "category", key = "'getCatalogJson'")
    // })
    @CacheEvict(value = "category", allEntries = true)       //删除某个分区下的所有数据
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCascade(CategoryEntity category) {

        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("catalogJson-lock");
        //创建写锁
        RLock rLock = readWriteLock.writeLock();

        try {
            rLock.lock();
            this.baseMapper.updateById(category);
            categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }

        //同时修改缓存中的数据
        //删除缓存,等待下一次主动查询进行更新
    }

    /**
     * 获取所有级别分类数据
     * @return
     */
    @Cacheable(value = "category", key = "#root.methodName")
    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        //1,查询所有分类
        List<CategoryEntity> selectList = this.baseMapper.selectList(null);

        //2, 获取所有一级分类
        List<CategoryEntity> level1Categorys = getParent_cid(selectList, 0L);

        //3,封装数据
        Map<String, List<Catelog2Vo>> resultMap = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            //3.1, 根据每一个一级分类,获取二级分类
            List<CategoryEntity> level2Categorys = getParent_cid(selectList, v.getCatId());

            //3.2, 封装上一步的结果为二级分类视图对象
            List<Catelog2Vo> catelog2Vos = null;
            if (level2Categorys != null) {
                catelog2Vos = level2Categorys.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());

                    //3.2.1, 根据每一个二级分类, 获取三级分类
                    List<CategoryEntity> level3Categorys = getParent_cid(selectList, l2.getCatId());
                    if (level3Categorys != null) {
                        List<Catelog2Vo.Category3Vo> category3Vos = level3Categorys.stream().map(l3 -> {
                            //2、封装成指定格式
                            Catelog2Vo.Category3Vo category3Vo = new Catelog2Vo.Category3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                            return category3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(category3Vos);
                    }

                    return catelog2Vo;
                }).collect(Collectors.toList());
            }

            return catelog2Vos;
        }));

        return resultMap;
    }

    //TODO 产生堆外内存溢出OutOfDirectMemoryError:
    //1)、springboot2.0以后默认使用lettuce操作redis的客户端，它使用通信
    //2)、lettuce的bug导致netty堆外内存溢出   可设置：-Dio.netty.maxDirectMemory
    //解决方案：不能直接使用-Dio.netty.maxDirectMemory去调大堆外内存
    //1)、升级lettuce客户端
    //2）、切换使用jedis
    // @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson2() {
        /**
         * 1、空结果缓存：解决缓存穿透问题
         * 2、设置过期时间(加随机值)：解决缓存雪崩
         * 3、加锁：解决缓存击穿问题
         */

        //1、加入缓存逻辑,缓存中存的数据是json字符串
        //JSON跨语言。跨平台兼容。
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String catalogJson = ops.get("catalogJson");
        if (StringUtils.isEmpty(catalogJson)) {
            //2、缓存中没有数据，查询数据库
            Map<String, List<Catelog2Vo>> catalogJsonFromDb = getCatalogJsonFromDbWithRedissonLock();
            return catalogJsonFromDb;
        }

        //转为指定的对象
        Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJson,new TypeReference<Map<String, List<Catelog2Vo>>>(){});
        return result;
    }

    /**
     * 缓存里的数据如何和数据库的数据保持一致？？
     * 缓存数据一致性
     * 1)、双写模式
     * 2)、失效模式
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedissonLock() {
        //1、占分布式锁。去redis占坑创建读锁
        //lock.lock(); //阻塞式等待
        //锁默认的过期时间是30s,redisson会自动续期,避免业务执行时间长锁被自动删掉的问题
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("catalogJson-lock");
        RLock rLock = readWriteLock.readLock();
        Map<String, List<Catelog2Vo>> dataFromDb = null;
        try {
            rLock.lock();
            //加锁成功...执行业务
            dataFromDb = getDataFromDb();
        } finally {
            //2、释放分布式锁
            //提问：假设解锁代码没有执行会不会出现死锁？ 存在默认的过期时间=30s,因此不会出现死锁
            rLock.unlock();
        }

        return dataFromDb;
    }


    /**
     * 从数据库查询并封装数据::分布式锁
     * 分布式锁的基本原理
     * 所有服务都去同一个地方占坑,这个地方可以是redis,也可以是数据库
     * redis是利用set NX（即不存在时设置值）,即使多线程也只能成功一个
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedisLock() {
        //1,占分布式锁(去redis占坑),设置过期时间必须和加锁是同步的(保证原子性（避免死锁）)
        String uuid = UUID.randomUUID().toString();
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid,300,TimeUnit.SECONDS);
        if (lock) {
            log.info("获取分布式锁成功...");
            Map<String, List<Catelog2Vo>> dataFromDb = null;
            try {
                //加锁成功...执行业务
                dataFromDb = getDataFromDb();
            } finally {
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                //2,释放分布式锁(删除锁),对比与删除必须保证是原子的（目的是保证删除的当前锁是自己的）
                stringRedisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"), uuid);
            }
            //先去redis查询下保证当前的锁是自己的
            //获取值对比，对比成功删除=原子性 lua脚本解锁
            // String lockValue = stringRedisTemplate.opsForValue().get("lock");
            // if (uuid.equals(lockValue)) {
            //     //删除我自己的锁
            //     stringRedisTemplate.delete("lock");
            // }

            return dataFromDb;
        } else {
            log.info("获取分布式锁失败!!!等待重试...");
            //加锁失败...重试机制
            //休眠一百毫秒,尽可能避免内存溢出
            try { TimeUnit.MILLISECONDS.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
            return getCatalogJsonFromDbWithRedisLock(); //自旋的方式
        }
    }

    private Map<String, List<Catelog2Vo>> getDataFromDb() {
        //得到锁以后，我们应该再去缓存中确定一次，如果没有才需要继续查询
        String catalogJson = stringRedisTemplate.opsForValue().get("catalogJson");
        if (!StringUtils.isEmpty(catalogJson)) {
            //缓存不为空直接返回
            Map<String, List<Catelog2Vo>> result = JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catelog2Vo>>>() {
            });

            return result;
        }
        log.info("查询了数据库");

        /**
         * 将数据库的多次查询变为一次
         */
        List<CategoryEntity> selectList = this.baseMapper.selectList(null);

        //1、查出所有分类
        //1、1）查出所有一级分类
        List<CategoryEntity> level1Categorys = getParent_cid(selectList, 0L);

        //封装数据
        Map<String, List<Catelog2Vo>> resultMap = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            //1、每一个的一级分类,查到这个一级分类的二级分类
            List<CategoryEntity> level2Categorys = getParent_cid(selectList, v.getCatId());

            //2、封装上面的结果
            List<Catelog2Vo> catelog2Vos = null;
            if (level2Categorys != null) {
                catelog2Vos = level2Categorys.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName().toString());

                    //1、找当前二级分类的三级分类封装成vo
                    List<CategoryEntity> level3Categorys = getParent_cid(selectList, l2.getCatId());
                    if (level3Categorys != null) {
                        List<Catelog2Vo.Category3Vo> category3Vos = level3Categorys.stream().map(l3 -> {
                            //2、封装成指定格式
                            Catelog2Vo.Category3Vo category3Vo = new Catelog2Vo.Category3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());

                            return category3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(category3Vos);
                    }

                    return catelog2Vo;
                }).collect(Collectors.toList());
            }

            return catelog2Vos;
        }));

        //3、将查到的数据放入缓存,将对象转为json
        String valueJson = JSON.toJSONString(resultMap);
        stringRedisTemplate.opsForValue().set("catalogJson", valueJson, 1, TimeUnit.DAYS);
        return resultMap;
    }

    /**
     * 从数据库查询并封装数据::本地锁
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithLocalLock() {
        // //如果缓存中有就用缓存的
        // Map<String, List<Catelog2Vo>> catalogJson = (Map<String, List<Catelog2Vo>>) cache.get("catalogJson");
        // if (cache.get("catalogJson") == null) {
        //     //调用业务
        //     //返回数据又放入缓存
        // }

        //只要是同一把锁，就能锁住这个锁的所有线程
        //1、synchronized (this)：SpringBoot所有的组件在容器中都是单例的。
        //TODO 本地锁：synchronized，JUC（Lock),在分布式情况下，想要锁住所有，必须使用分布式锁
        synchronized (this) {
            //得到锁以后，我们应该再去缓存中确定一次，如果没有才需要继续查询
            return getDataFromDb();
        }
    }

    /**
     * 根据父类Id获取子级
     * @param selectList
     * @param parentCid
     * @return
     */
    private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList, Long parentCid) {
        List<CategoryEntity> categoryEntities = selectList.stream()
                .filter(item -> item.getParentCid().equals(parentCid))
                .collect(Collectors.toList());
        return categoryEntities;
        // return this.baseMapper.selectList(
        //         new QueryWrapper<CategoryEntity>().eq("parent_cid", parentCid));
    }

    private List<Long> findParentPath(Long catelogId, List<Long> paths) {

        //1、收集当前节点id
        paths.add(catelogId);

        //根据当前分类id查询信息
        CategoryEntity byId = this.getById(catelogId);
        //如果当前不是父分类
        if (byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), paths);
        }

        return paths;
    }

}