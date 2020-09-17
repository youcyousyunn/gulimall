package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.SeckillSkuRelationEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface SeckillSkuRelationService extends IService<SeckillSkuRelationEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

