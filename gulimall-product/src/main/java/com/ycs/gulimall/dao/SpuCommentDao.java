package com.ycs.gulimall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycs.gulimall.entity.SpuCommentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价
 */
@Mapper
public interface SpuCommentDao extends BaseMapper<SpuCommentEntity> {
	
}
