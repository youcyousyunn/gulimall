package com.ycs.gulimall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycs.gulimall.entity.SkuInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * sku信息
 */
@Mapper
public interface SkuInfoDao extends BaseMapper<SkuInfoEntity> {
	
}
