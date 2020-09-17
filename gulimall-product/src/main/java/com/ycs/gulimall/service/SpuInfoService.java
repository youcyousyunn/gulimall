package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.SpuInfoEntity;
import com.ycs.gulimall.utils.PageUtils;
import com.ycs.gulimall.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {
    PageUtils queryPage(Map<String, Object> params);
    void savesupInfo(SpuSaveVo vo);
    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);
    PageUtils queryPageByCondtion(Map<String, Object> params);

    /**
     * 商品上架
     * @param spuId
     */
    void up(Long spuId);

    /**
     * 根据skuId查询spu的信息
     * @param skuId
     * @return
     */
    SpuInfoEntity getSpuInfoBySkuId(Long skuId);
}

