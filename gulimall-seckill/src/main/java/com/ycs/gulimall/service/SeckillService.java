package com.ycs.gulimall.service;

import com.ycs.gulimall.to.SeckillSkuRedisTo;

import java.util.List;

public interface SeckillService {

    /**
     * 上架三天需要秒杀的商品
     */
    void uploadSeckillSkuLatest3Days();
    List<SeckillSkuRedisTo> getCurrentSeckillSkus();
    SeckillSkuRedisTo getSkuSeckilInfo(Long skuId);

    /**
     * 当前商品进行秒杀（秒杀开始）
     * @param killId
     * @param key
     * @param num
     * @return
     */
    String kill(String killId, String key, Integer num) throws InterruptedException;
}
