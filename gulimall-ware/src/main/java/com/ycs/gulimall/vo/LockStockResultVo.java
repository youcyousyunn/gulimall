package com.ycs.gulimall.vo;

import lombok.Data;

@Data
public class LockStockResultVo {
    private Long skuId;
    private Integer num;
    /** 是否锁定成功 **/
    private Boolean locked;
}