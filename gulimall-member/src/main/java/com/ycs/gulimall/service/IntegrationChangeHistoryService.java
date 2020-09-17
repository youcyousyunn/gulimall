package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.IntegrationChangeHistoryEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

