package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.RefundInfoEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

/**
 * 退款信息
 */
public interface RefundInfoService extends IService<RefundInfoEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

