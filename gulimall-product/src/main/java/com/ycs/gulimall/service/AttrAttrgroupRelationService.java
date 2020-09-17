package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.AttrAttrgroupRelationEntity;
import com.ycs.gulimall.utils.PageUtils;
import com.ycs.gulimall.vo.AttrGroupRelationVo;

import java.util.List;
import java.util.Map;

/**
 * 属性&属性分组关联
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {
    PageUtils queryPage(Map<String, Object> params);
    void saveBatch(List<AttrGroupRelationVo> vos);
}

