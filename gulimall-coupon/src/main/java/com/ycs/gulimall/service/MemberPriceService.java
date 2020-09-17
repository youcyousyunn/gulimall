package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.MemberPriceEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface MemberPriceService extends IService<MemberPriceEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

