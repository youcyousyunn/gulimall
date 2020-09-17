package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.BrandEntity;
import com.ycs.gulimall.entity.CategoryBrandRelationEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存详细信息
     * @param categoryBrandRelation
     */
    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);
    void updateBrand(Long brandId, String name);
    void updateCategory(Long catId, String name);
    List<BrandEntity> getBrandsByCatId(Long catId);
}

