package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.SpuCommentEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

/**
 * 商品评价
 */
public interface SpuCommentService extends IService<SpuCommentEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

