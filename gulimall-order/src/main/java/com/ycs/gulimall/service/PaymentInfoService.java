package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.PaymentInfoEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

/**
 * 支付信息表
 */
public interface PaymentInfoService extends IService<PaymentInfoEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

