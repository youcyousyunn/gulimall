package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.ProductAttrValueEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {
    PageUtils queryPage(Map<String, Object> params);
    void saveProductAttr(List<ProductAttrValueEntity> collect);
    List<ProductAttrValueEntity> baseAttrListforspu(Long spuId);

    /**
     * 修改商品规格
     * @param spuId
     * @param entities
     */
    void updateSpuAttr(Long spuId, List<ProductAttrValueEntity> entities);
}

