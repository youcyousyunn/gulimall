package com.ycs.gulimall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycs.gulimall.entity.AttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {
    List<Long> selectSearchAttrIds(@Param("attrIds") List<Long> attrIds);
}
