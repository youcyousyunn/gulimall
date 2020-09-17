package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.SkuImagesEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * sku图片
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {
    PageUtils queryPage(Map<String, Object> params);
    List<SkuImagesEntity> getImagesBySkuId(Long skuId);
}

