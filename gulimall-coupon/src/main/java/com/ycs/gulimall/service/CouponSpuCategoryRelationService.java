package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.CouponSpuCategoryRelationEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface CouponSpuCategoryRelationService extends IService<CouponSpuCategoryRelationEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

