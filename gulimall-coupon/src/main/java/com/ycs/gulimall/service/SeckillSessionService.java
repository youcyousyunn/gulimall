package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.SeckillSessionEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.List;
import java.util.Map;

public interface SeckillSessionService extends IService<SeckillSessionEntity> {
    PageUtils queryPage(Map<String, Object> params);
    List<SeckillSessionEntity> getLates3DaySession();
}

