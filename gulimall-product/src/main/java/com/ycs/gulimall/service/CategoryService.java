package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.CategoryEntity;
import com.ycs.gulimall.utils.PageUtils;
import com.ycs.gulimall.vo.Catelog2Vo;

import java.util.List;
import java.util.Map;

public interface CategoryService extends IService<CategoryEntity> {
    PageUtils queryPage(Map<String, Object> params);
    List<CategoryEntity> listWithTree();
    void removeMenuByIds(List<Long> asList);

    /**
     * 找到catelogId的完整路径
     * [父/子/孙]
     * @param catelogId
     * @return
     */
    Long[] findCatelogPath(Long catelogId);
    public void updateCascade(CategoryEntity category);
    List<CategoryEntity> getLevel1Categorys();
    Map<String, List<Catelog2Vo>> getCatalogJson();
}

