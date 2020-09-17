package com.ycs.gulimall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycs.gulimall.entity.AttrGroupEntity;
import com.ycs.gulimall.vo.SpuItemAttrGroupVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {
    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(@Param("spuId") Long spuId, @Param("catalogId") Long catalogId);
}
