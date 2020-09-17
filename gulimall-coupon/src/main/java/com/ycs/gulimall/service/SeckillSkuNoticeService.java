package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.SeckillSkuNoticeEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface SeckillSkuNoticeService extends IService<SeckillSkuNoticeEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

