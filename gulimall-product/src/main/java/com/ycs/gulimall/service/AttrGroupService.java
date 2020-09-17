package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.AttrGroupEntity;
import com.ycs.gulimall.utils.PageUtils;
import com.ycs.gulimall.vo.AttrGroupWithAttrsVo;
import com.ycs.gulimall.vo.SpuItemAttrGroupVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {
    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPage(Map<String, Object> params, Long catelogId);
    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);
    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId);
}

