package com.ycs.gulimall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycs.gulimall.entity.BrandEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 品牌
 */
@Mapper
public interface BrandDao extends BaseMapper<BrandEntity> {
	
}
