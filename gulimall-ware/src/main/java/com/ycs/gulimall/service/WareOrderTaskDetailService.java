package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.WareOrderTaskDetailEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetailEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

