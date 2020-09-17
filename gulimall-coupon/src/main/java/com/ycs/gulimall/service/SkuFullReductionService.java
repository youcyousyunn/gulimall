package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.SkuFullReductionEntity;
import com.ycs.gulimall.to.SkuReductionTo;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {
    PageUtils queryPage(Map<String, Object> params);
    void saveSkuReduction(SkuReductionTo skuReductionTo);
}

