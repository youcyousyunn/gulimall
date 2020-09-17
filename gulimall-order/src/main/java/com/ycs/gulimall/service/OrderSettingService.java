package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.OrderSettingEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

/**
 * 订单配置信息
 */
public interface OrderSettingService extends IService<OrderSettingEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

