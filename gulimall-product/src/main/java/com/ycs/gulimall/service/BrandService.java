package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.BrandEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

/**
 * 品牌
 */
public interface BrandService extends IService<BrandEntity> {
    PageUtils queryPage(Map<String, Object> params);
    void updateDetail(BrandEntity brand);
}

