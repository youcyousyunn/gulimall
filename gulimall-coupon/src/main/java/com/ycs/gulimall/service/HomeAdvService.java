package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.HomeAdvEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface HomeAdvService extends IService<HomeAdvEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

