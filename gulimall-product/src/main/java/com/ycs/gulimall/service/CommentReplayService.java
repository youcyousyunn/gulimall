package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.CommentReplayEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.Map;

/**
 * 商品评价回复关系
 */
public interface CommentReplayService extends IService<CommentReplayEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

