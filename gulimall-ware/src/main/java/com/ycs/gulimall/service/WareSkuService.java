package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.WareSkuEntity;
import com.ycs.gulimall.to.OrderTo;
import com.ycs.gulimall.to.mq.StockLockedTo;
import com.ycs.gulimall.utils.PageUtils;
import com.ycs.gulimall.vo.SkuHasStockVo;
import com.ycs.gulimall.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;

public interface WareSkuService extends IService<WareSkuEntity> {
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 添加库存
     * @param skuId
     * @param wareId
     * @param skuNum
     */
    void addStock(Long skuId, Long wareId, Integer skuNum);
    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);
    boolean orderLockStock(WareSkuLockVo vo);
    void unlockStock(StockLockedTo to);
    void unlockStock(OrderTo orderTo);
}

