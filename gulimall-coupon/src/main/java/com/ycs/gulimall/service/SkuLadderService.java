package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.SkuLadderEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface SkuLadderService extends IService<SkuLadderEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

