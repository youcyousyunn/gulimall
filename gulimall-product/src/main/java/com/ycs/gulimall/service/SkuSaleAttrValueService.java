package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.SkuSaleAttrValueEntity;
import com.ycs.gulimall.utils.PageUtils;
import com.ycs.gulimall.vo.SkuItemSaleAttrVo;

import java.util.List;
import java.util.Map;

/**
 * sku销售属性&值
 * @date 2020-05-22 19:00:18
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {
    PageUtils queryPage(Map<String, Object> params);
    List<SkuItemSaleAttrVo> getSaleAttrBySpuId(Long spuId);
    List<String> getSkuSaleAttrValuesAsStringList(Long skuId);
}

