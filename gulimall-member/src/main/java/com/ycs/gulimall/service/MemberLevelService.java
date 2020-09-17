package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.MemberLevelEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface MemberLevelService extends IService<MemberLevelEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

