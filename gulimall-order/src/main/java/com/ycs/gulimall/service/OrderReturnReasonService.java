package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.OrderReturnReasonEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

/**
 * 退货原因
 */
public interface OrderReturnReasonService extends IService<OrderReturnReasonEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

