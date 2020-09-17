package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.CouponHistoryEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface CouponHistoryService extends IService<CouponHistoryEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

