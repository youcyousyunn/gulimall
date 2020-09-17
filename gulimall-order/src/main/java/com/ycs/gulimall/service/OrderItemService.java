package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.OrderItemEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

/**
 * 订单项信息
 */
public interface OrderItemService extends IService<OrderItemEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

