package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.GrowthChangeHistoryEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface GrowthChangeHistoryService extends IService<GrowthChangeHistoryEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

