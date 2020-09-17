package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.MemberLoginLogEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface MemberLoginLogService extends IService<MemberLoginLogEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

