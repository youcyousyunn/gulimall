package com.ycs.gulimall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycs.gulimall.entity.ProductAttrValueEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * spu属性值
 */
@Mapper
public interface ProductAttrValueDao extends BaseMapper<ProductAttrValueEntity> {
	
}
