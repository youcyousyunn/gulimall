package com.ycs.gulimall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycs.gulimall.dao.SpuBoundsDao;
import com.ycs.gulimall.entity.SpuBoundsEntity;
import com.ycs.gulimall.service.SpuBoundsService;
import com.ycs.gulimall.utils.PageUtils;
import com.ycs.gulimall.utils.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service("spuBoundsService")
public class SpuBoundsServiceImpl extends ServiceImpl<SpuBoundsDao, SpuBoundsEntity> implements SpuBoundsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<SpuBoundsEntity> queryWrapper = new QueryWrapper<>();

        String key = (String) params.get("key");

        if (!StringUtils.isEmpty(key)) {
            queryWrapper.eq("id",key).or().eq("sku_id",key);
        }

        IPage<SpuBoundsEntity> page = this.page(
                new Query<SpuBoundsEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}