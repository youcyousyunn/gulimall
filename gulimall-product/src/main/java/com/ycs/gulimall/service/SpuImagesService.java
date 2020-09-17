package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.SpuImagesEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * spu图片
 */
public interface SpuImagesService extends IService<SpuImagesEntity> {
    PageUtils queryPage(Map<String, Object> params);
    void saveImages(Long id, List<String> images);
}

