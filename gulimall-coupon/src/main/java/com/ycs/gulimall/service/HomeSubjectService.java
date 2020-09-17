package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.HomeSubjectEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface HomeSubjectService extends IService<HomeSubjectEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

