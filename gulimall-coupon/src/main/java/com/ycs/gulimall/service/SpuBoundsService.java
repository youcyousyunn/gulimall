package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.SpuBoundsEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface SpuBoundsService extends IService<SpuBoundsEntity> {
    PageUtils queryPage(Map<String, Object> params);
}