package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.SkuInfoEntity;
import com.ycs.gulimall.utils.PageUtils;
import com.ycs.gulimall.vo.SkuItemVo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface SkuInfoService extends IService<SkuInfoEntity> {
    PageUtils queryPage(Map<String, Object> params);
    void saveSkuInfo(SkuInfoEntity skuInfoEntity);
    PageUtils queryPageCondition(Map<String, Object> params);
    List<SkuInfoEntity> getSkusBySpuId(Long spuId);
    SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException;
}

