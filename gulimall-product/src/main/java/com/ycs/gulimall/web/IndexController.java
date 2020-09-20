package com.ycs.gulimall.web;

import com.ycs.gulimall.entity.CategoryEntity;
import com.ycs.gulimall.service.CategoryService;
import com.ycs.gulimall.vo.Catelog2Vo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
public class IndexController {
    @Resource
    private CategoryService categoryService;
    @Autowired
    private RedissonClient redisson;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 进入商城首页
     * @param model
     * @return
     */
    @GetMapping(value = {"/","index.html"})
    private String indexPage(Model model) {
        //查出所有商品的一级分类
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();
        model.addAttribute("categories", categoryEntities);
        return "index";
    }

    /**
     * 获取所有级别分类数据
     * @return
     */
    @GetMapping(value = "/index/catalog.json")
    @ResponseBody
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        Map<String, List<Catelog2Vo>> catalogJson = categoryService.getCatalogJson();
        return catalogJson;
    }

    /**
     * 测试redisson锁的过期
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/lock")
    public String lock() {
        //1、获取一把锁，只要锁的名字一样，就是同一把锁
        RLock myLock = redisson.getLock("my-lock");

        //2、加锁,阻塞式等待。默认加的锁都是30s
        myLock.lock();
        /**
         * 1,锁的自动续期
         * 如果业务超长，运行期间自动锁上新的30s。不用担心业务时间长，锁自动过期被删掉
         * 加锁的业务只要运行完成，就不会给当前锁续期，即使不手动解锁，锁默认会在30s内自动过期，不会产生死锁问题
         * 只要占锁成功，就会启动一个定时任务【重新给锁设置过期时间，新的过期时间就是看门狗的默认时间=30秒】,每隔10秒就会自动的再次续期
         * 2,自定义锁的过期时间
         * myLock.lock(10,TimeUnit.SECONDS);   //10秒钟自动解锁,自动解锁时间一定要大于业务执行时间（在锁时间到了以后，不会自动续期）
         */
        try {
            log.info("加锁成功，执行业务..." + Thread.currentThread().getId());
            try { TimeUnit.SECONDS.sleep(20); } catch (InterruptedException e) { e.printStackTrace(); }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //3、解锁  假设解锁代码没有运行，Redisson会不会出现死锁
            log.info("释放锁..." + Thread.currentThread().getId());
            myLock.unlock();
        }

        return "success";
    }


    /**
     * 保证一定能读到最新数据，修改期间，写锁是一个排它锁（互斥锁、独享锁），读锁是一个共享锁
     * 写锁没释放读锁必须等待
     * 读 + 读 ：相当于无锁，并发读，只会在Redis中记录好，所有当前的读锁。他们都会同时加锁成功
     * 写 + 读 ：必须等待写锁释放
     * 写 + 写 ：阻塞方式
     * 读 + 写 ：有读锁。写也需要等待
     * @return
     */
    @GetMapping(value = "/lock/write")
    @ResponseBody
    public String writeValue() {
        String s = "";
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("rw-lock");
        RLock rLock = readWriteLock.writeLock();
        try {
            //1、改数据加写锁，读数据加读锁
            rLock.lock();
            s = UUID.randomUUID().toString();
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set("write-key",s);
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }

        return s;
    }

    @GetMapping(value = "/lock/read")
    @ResponseBody
    public String readValue() {
        String s = "";
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("rw-lock");
        //加读锁
        RLock rLock = readWriteLock.readLock();
        try {
            rLock.lock();
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            s = ops.get("write-key");
            try { TimeUnit.SECONDS.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }

        return s;
    }


    /**
     * 车库停车示例讲解多线程之信号量
     * 信号量也可以用作分布式限流
     * 设置初始值key=park,value=3
     * @return
     * @throws InterruptedException
     */
    @GetMapping(value = "/car/park")
    @ResponseBody
    public String park() throws InterruptedException {
        RSemaphore park = redisson.getSemaphore("park");
        park.acquire(); //获取一个信号(获取一个值,占一个车位), 阻塞等待
//        boolean flag = park.tryAcquire(); // 尝试去获取车位,非阻塞(获取成功返回true,否则返回false)
//        if (flag) {
//            //执行业务
//        } else {
//            return "error";
//        }

        return "获取到车位!";
    }

    @GetMapping(value = "/car/go")
    @ResponseBody
    public String go() {
        RSemaphore park = redisson.getSemaphore("park");
        park.release(); //释放一个车位
        return "停车场一辆车开走,空出一个车位...";
    }

    /**
     * 店铺打烊示例讲解多线程计数器
     * @return
     * @throws InterruptedException
     */
    @GetMapping(value = "/shop/lockDoor")
    @ResponseBody
    public String lockDoor() throws InterruptedException {
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.trySetCount(5);
        door.await();       //等待闭锁完成
        return "顾客都走了,店铺打烊了!";
    }

    @GetMapping(value = "/shop/leave/{id}")
    @ResponseBody
    public String leave(@PathVariable("id") Long id) {
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.countDown(); //计数-1
        return id + "号顾客走了...";
    }
}
