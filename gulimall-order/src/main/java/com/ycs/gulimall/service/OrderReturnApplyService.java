package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.OrderReturnApplyEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

/**
 * 订单退货申请
 */
public interface OrderReturnApplyService extends IService<OrderReturnApplyEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

