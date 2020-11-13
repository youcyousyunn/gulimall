package com.ycs.gulimall.service.impl;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ycs.gulimall.feign.CouponFeignService;
import com.ycs.gulimall.feign.ProductFeignService;
import com.ycs.gulimall.interceptor.LoginUserInterceptor;
import com.ycs.gulimall.service.SeckillService;
import com.ycs.gulimall.to.SeckillSkuRedisTo;
import com.ycs.gulimall.to.mq.SeckillOrderTo;
import com.ycs.gulimall.utils.R;
import com.ycs.gulimall.vo.MemberResponseVo;
import com.ycs.gulimall.vo.SeckillSessionWithSkusVo;
import com.ycs.gulimall.vo.SkuInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Resource
    private CouponFeignService couponFeignService;
    @Resource
    private ProductFeignService productFeignService;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final String SECKILL_SESSION_PREFIX = "seckill:session:";
    private final String SECKILL_SKU_PREFIX = "seckill:sku:";
    private final String SECKILL_SKU_STOCK_SEMAPHORE = "seckill:sku:stock:";


    @Override
    public void uploadSeckillSkuLatest3Days() {
        //1,查询最近三天的秒杀的活动与商品
        R r = couponFeignService.getLates3DaySession();
        if (r.getCode() == 0) {
            //上架商品
            List<SeckillSessionWithSkusVo> seckillSessionWithSkusVos = r.getData("data", new TypeReference<List<SeckillSessionWithSkusVo>>() {
            });

            //1,缓存活动信息
            saveSessionInfo(seckillSessionWithSkusVos);

            //2、缓存活动的关联商品信息
            saveSessionSkuInfo(seckillSessionWithSkusVos);
        }
    }

    /**
     * 缓存秒杀活动信息
     * @param sessions
     */
    private void saveSessionInfo(List<SeckillSessionWithSkusVo> sessions) {
        sessions.stream().forEach(session -> {
            //获取当前活动的开始和结束时间的时间戳
            long startTime = session.getStartTime().getTime();
            long endTime = session.getEndTime().getTime();

            //存入到Redis中的key
            String key = SECKILL_SESSION_PREFIX + startTime + "_" + endTime;

            //判断Redis中是否有该信息，如果没有才进行添加
            Boolean hasKey = redisTemplate.hasKey(key);
            //缓存活动信息
            if (!hasKey) {
                //获取到活动中所有商品的skuId
                List<String> skuIds = session.getRelationSkus().stream()
                        .map(item -> item.getPromotionSessionId() + "-" + item.getSkuId().toString()).collect(Collectors.toList());
                redisTemplate.opsForList().leftPushAll(key, skuIds);
            }
        });
    }

    /**
     * 缓存秒杀活动所关联的商品信息
     * @param sessions
     */
    private void saveSessionSkuInfo(List<SeckillSessionWithSkusVo> sessions) {
        sessions.stream().forEach(session -> {
            //准备hash操作,绑定hash
            BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(SECKILL_SKU_PREFIX);
            session.getRelationSkus().stream().forEach(seckillSkuVo -> {
                //生成随机码
                String token = UUID.randomUUID().toString().replace("-", "");
                String redisKey = seckillSkuVo.getPromotionSessionId().toString() + "-" + seckillSkuVo.getSkuId().toString();
                if (!operations.hasKey(redisKey)) {
                    //缓存我们商品信息
                    SeckillSkuRedisTo redisTo = new SeckillSkuRedisTo();
                    Long skuId = seckillSkuVo.getSkuId();
                    //1,查询sku的基本信息
                    R r = productFeignService.getSkuInfo(skuId);
                    if (r.getCode() == 0) {
                        SkuInfoVo skuInfo = r.getData("skuInfo", new TypeReference<SkuInfoVo>(){});
                        redisTo.setSkuInfo(skuInfo);
                    }

                    //2,sku的秒杀信息
                    BeanUtils.copyProperties(seckillSkuVo, redisTo);

                    //3,设置当前商品的秒杀时间
                    redisTo.setStartTime(session.getStartTime().getTime());
                    redisTo.setEndTime(session.getEndTime().getTime());

                    //4,设置商品的秒杀随机码（防止恶意攻击）
                    redisTo.setRandomCode(token);

                    //序列化json格式存入Redis中
                    String seckillValue = JSON.toJSONString(redisTo);
                    String seckillKey = seckillSkuVo.getPromotionSessionId().toString() + "-" + seckillSkuVo.getSkuId().toString();
                    operations.put(seckillKey, seckillValue);

                    //5,使用库存作为分布式Redisson信号量（限流）
                    RSemaphore semaphore = redissonClient.getSemaphore(SECKILL_SKU_STOCK_SEMAPHORE + token);
                    // 商品可以秒杀的数量作为信号量
                    semaphore.trySetPermits(seckillSkuVo.getSeckillCount());
                }
            });
        });
    }

    @SentinelResource(value = "getCurrentSeckillSkusResource",blockHandler = "getCurrentSeckillSkusBlockHandler")
    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckillSkus() {
        try (Entry entry = SphU.entry("seckillSkus")) {
            //1,确定当前属于哪个秒杀场次
            long currentTime = System.currentTimeMillis();

            //从Redis中查询到所有key以seckill:session:开头的所有数据
            Set<String> keys = redisTemplate.keys(SECKILL_SESSION_PREFIX + "*");
            for (String key : keys) {
                String replace = key.replace(SECKILL_SESSION_PREFIX, "");
                String[] s = replace.split("_");
                //获取存入Redis商品的开始时间
                long startTime = Long.parseLong(s[0]);
                //获取存入Redis商品的结束时间
                long endTime = Long.parseLong(s[1]);

                //判断是否是当前秒杀场次
                if (currentTime >= startTime && currentTime <= endTime) {
                    //2,获取这个秒杀场次需要的所有商品信息
                    List<String> range = redisTemplate.opsForList().range(key, -100, 100);
                    BoundHashOperations<String, String, String> hasOps = redisTemplate.boundHashOps(SECKILL_SKU_PREFIX);
                    assert range != null;
                    List<String> listValue = hasOps.multiGet(range);
                    if (listValue != null && listValue.size() >= 0) {
                        List<SeckillSkuRedisTo> collect = listValue.stream().map(item -> {
                            String jsonStr = (String) item;
                            SeckillSkuRedisTo redisTo = JSON.parseObject(jsonStr, SeckillSkuRedisTo.class);
                            // redisTo.setRandomCode(null);当前秒杀开始需要随机码
                            return redisTo;
                        }).collect(Collectors.toList());
                        return collect;
                    }
                    break;
                }
            }
        } catch (BlockException e) {
            log.error("获取当前参加秒杀商品的资源被限流了！！！{}", e.getMessage());
        }

        return null;
    }

    /**
     * 获取秒杀商品被限流回调
     * @param e
     * @return
     */
    public List<SeckillSkuRedisTo> getCurrentSeckillSkusBlockHandler(BlockException e) {
        log.error("获取到当前可以参加秒杀商品的信息接口被限流了,{}", e.getMessage());
        return null;
    }

    @Override
    public SeckillSkuRedisTo getSkuSeckilInfo(Long skuId) {
        //1,找到所有参加秒杀的商品的key信息
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SECKILL_SKU_PREFIX);
        Set<String> keys = hashOps.keys();
        if (keys != null && keys.size() > 0) {
            //4-45 正则表达式进行匹配
            String reg = "\\d-" + skuId;
            for (String key : keys) {
                //如果匹配上了
                if (Pattern.matches(reg, key)) {
                    //从Redis中取出数据来
                    String redisValue = hashOps.get(key);
                    //进行序列化
                    SeckillSkuRedisTo redisTo = JSON.parseObject(redisValue, SeckillSkuRedisTo.class);

                    //随机码
                    Long currentTime = System.currentTimeMillis();
                    Long startTime = redisTo.getStartTime();
                    Long endTime = redisTo.getEndTime();
                    //如果当前时间大于等于秒杀活动开始时间并且要小于活动结束时间
                    if (currentTime >= startTime && currentTime <= endTime) {
                        return redisTo;
                    }
                    redisTo.setRandomCode(null);
                    return redisTo;
                }
            }
        }

        return null;
    }


    /**
     * 当前商品进行秒杀（秒杀开始）
     * @param killId
     * @param key
     * @param num
     * @return
     */
    @Override
    public String kill(String killId, String key, Integer num) throws InterruptedException {
        long s1 = System.currentTimeMillis();
        //获取当前用户的信息
        MemberResponseVo user = LoginUserInterceptor.loginUser.get();

        //1,获取当前秒杀商品的详细信息(从Redis中获取)
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SECKILL_SKU_PREFIX);
        String skuInfoValue = hashOps.get(killId);
        if (StringUtils.isEmpty(skuInfoValue)) {
            return null;
        }

        //(合法性效验)
        SeckillSkuRedisTo redisTo = JSON.parseObject(skuInfoValue, SeckillSkuRedisTo.class);
        Long startTime = redisTo.getStartTime();
        Long endTime = redisTo.getEndTime();
        long currentTime = System.currentTimeMillis();
        //判断当前这个秒杀请求是否在活动时间区间内(效验时间的合法性)
        if (currentTime >= startTime && currentTime <= endTime) {
            //2,校验随机码和商品id
            String randomCode = redisTo.getRandomCode();
            String skuId = redisTo.getPromotionSessionId() + "-" +redisTo.getSkuId();
            if (randomCode.equals(key) && killId.equals(skuId)) {
                //3,验证购物数量是否合理和库存量是否充足
                Integer seckillLimit = redisTo.getSeckillLimit();

                //获取信号量
                String seckillCount = redisTemplate.opsForValue().get(SECKILL_SKU_STOCK_SEMAPHORE + randomCode);
                Integer count = Integer.valueOf(seckillCount);
                //判断信号量是否大于0,并且买的数量不能超过库存
                if (count > 0 && num <= seckillLimit && count > num ) {
                    //4,验证这个人是否已经买过了（幂等性处理）.如果秒杀成功,就去占位:userId-sessionId-skuId
                    //SETNX 原子性处理
                    String redisKey = user.getId() + "-" + skuId;
                    //设置自动过期(活动结束时间-当前时间)
                    Long ttl = endTime - currentTime;
                    Boolean flag = redisTemplate.opsForValue().setIfAbsent(redisKey, num.toString(), ttl, TimeUnit.MILLISECONDS);
                    if (flag) {
                        //占位成功说明从来没有买过,分布式锁(获取信号量-1)
                        RSemaphore semaphore = redissonClient.getSemaphore(SECKILL_SKU_STOCK_SEMAPHORE + randomCode);
                        //秒杀成功，快速下单
                        boolean semaphoreCount = semaphore.tryAcquire(num, 100, TimeUnit.MILLISECONDS);
                        //保证Redis中还有商品库存
                        if (semaphoreCount) {
                            //创建订单号和订单信息发送给MQ
                            // 秒杀成功 快速下单 发送消息到 MQ 整个操作时间在 10ms 左右
                            String orderId = IdWorker.getTimeId();
                            SeckillOrderTo orderTo = new SeckillOrderTo();
                            orderTo.setOrderSn(orderId);
                            orderTo.setMemberId(user.getId());
                            orderTo.setNum(num);
                            orderTo.setPromotionSessionId(redisTo.getPromotionSessionId());
                            orderTo.setSkuId(redisTo.getSkuId());
                            orderTo.setSeckillPrice(redisTo.getSeckillPrice());
                            rabbitTemplate.convertAndSend("gulimall.order.event.exchange","gulimall.order.seckill.router.key",orderTo);
                            long s2 = System.currentTimeMillis();
                            log.info("秒杀成功耗时：{}", (s2 - s1));
                            return orderId;
                        }
                    }
                }
            }
        }

        long s3 = System.currentTimeMillis();
        log.info("秒杀失败耗时：{}" + (s3 - s1));
        return null;
    }
}
