package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.CouponEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface CouponService extends IService<CouponEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

