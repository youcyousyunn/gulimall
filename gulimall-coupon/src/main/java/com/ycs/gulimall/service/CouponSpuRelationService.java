package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.CouponSpuRelationEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface CouponSpuRelationService extends IService<CouponSpuRelationEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

