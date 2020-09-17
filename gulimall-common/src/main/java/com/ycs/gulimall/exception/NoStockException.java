package com.ycs.gulimall.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class NoStockException extends RuntimeException {
    private Long skuId;

    public NoStockException(String msg) {
        super(msg);
    }

    public NoStockException(Long skuId) {
        super("商品ID："+ skuId + "库存不足！");
    }
}
