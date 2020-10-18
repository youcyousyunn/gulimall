package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.WareOrderTaskEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

public interface WareOrderTaskService extends IService<WareOrderTaskEntity> {
    PageUtils queryPage(Map<String, Object> params);
    WareOrderTaskEntity getOrderTaskByOrderSn(String orderSn);
}

