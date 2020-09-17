package com.ycs.gulimall.vo;

import com.ycs.gulimall.entity.OrderEntity;
import lombok.Data;

@Data
public class SubmitOrderResponseVo {
    private OrderEntity order;
    /** 错误状态码 **/
    private Integer code;
}
