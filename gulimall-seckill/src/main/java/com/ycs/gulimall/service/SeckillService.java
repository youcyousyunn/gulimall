package com.ycs.gulimall.service;

import com.ycs.gulimall.to.SeckillSkuRedisTo;

import java.util.List;

public interface SeckillService {
    void uploadSeckillSkuLatest3Days();
    List<SeckillSkuRedisTo> getCurrentSeckillSkus();
    SeckillSkuRedisTo getSkuSeckilInfo(Long skuId);
    String kill(String killId, String key, Integer num) throws InterruptedException;
}
