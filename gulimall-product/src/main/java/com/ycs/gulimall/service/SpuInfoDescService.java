package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.SpuInfoDescEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

/**
 * spu信息介绍
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {
    PageUtils queryPage(Map<String, Object> params);
    void saveSpuInfoDesc(SpuInfoDescEntity spuInfoDescEntity);
}

