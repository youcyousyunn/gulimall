package com.ycs.gulimall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycs.gulimall.entity.SkuSaleAttrValueEntity;
import com.ycs.gulimall.vo.SkuItemSaleAttrVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sku销售属性&值
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {
    List<SkuItemSaleAttrVo> getSaleAttrBySpuId(@Param("spuId") Long spuId);
    List<String> getSkuSaleAttrValuesAsStringList(@Param("skuId") Long skuId);
}
