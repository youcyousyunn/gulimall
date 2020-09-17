package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.MemberCollectSpuEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface MemberCollectSpuService extends IService<MemberCollectSpuEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

