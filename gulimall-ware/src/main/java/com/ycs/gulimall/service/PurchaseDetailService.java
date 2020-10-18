package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.PurchaseDetailEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.List;
import java.util.Map;

public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {
    PageUtils queryPage(Map<String, Object> params);
    List<PurchaseDetailEntity> listDetailByPurchaseId(Long id);
}

