package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.MemberCollectSubjectEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface MemberCollectSubjectService extends IService<MemberCollectSubjectEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

