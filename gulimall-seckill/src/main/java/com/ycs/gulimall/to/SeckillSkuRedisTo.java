package com.ycs.gulimall.to;

import com.ycs.gulimall.vo.SkuInfoVo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SeckillSkuRedisTo {
    /**
     * 活动id
     */
    private Long promotionId;
    /**
     * 活动场次id
     */
    private Long promotionSessionId;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀总量
     */
    private Integer seckillCount;
    /**
     * 每人限购数量
     */
    private Integer seckillLimit;
    /**
     * 排序
     */
    private Integer seckillSort;

    /**
     * sku的详细信息
     */
    private SkuInfoVo skuInfo;

    /**
     * 秒杀随机码
     */
    private String randomCode;

    /**
     * 秒杀开始时间
     */
    private Long startTime;

    /**
     * 秒杀结束时间
     */
    private Long endTime;
}
