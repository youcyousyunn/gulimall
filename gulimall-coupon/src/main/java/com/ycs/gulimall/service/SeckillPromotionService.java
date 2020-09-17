package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.SeckillPromotionEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface SeckillPromotionService extends IService<SeckillPromotionEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

