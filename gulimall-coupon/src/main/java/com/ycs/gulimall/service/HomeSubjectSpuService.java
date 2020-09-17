package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.HomeSubjectSpuEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface HomeSubjectSpuService extends IService<HomeSubjectSpuEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

