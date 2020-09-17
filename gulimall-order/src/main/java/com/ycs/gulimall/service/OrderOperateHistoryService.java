package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.OrderOperateHistoryEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

/**
 * 订单操作历史记录
 */
public interface OrderOperateHistoryService extends IService<OrderOperateHistoryEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

