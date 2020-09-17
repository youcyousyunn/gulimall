package com.ycs.gulimall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycs.gulimall.dao.SpuCommentDao;
import com.ycs.gulimall.entity.SpuCommentEntity;
import com.ycs.gulimall.service.SpuCommentService;
import com.ycs.gulimall.utils.PageUtils;
import com.ycs.gulimall.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("spuCommentService")
public class SpuCommentServiceImpl extends ServiceImpl<SpuCommentDao, SpuCommentEntity> implements SpuCommentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuCommentEntity> page = this.page(
                new Query<SpuCommentEntity>().getPage(params),
                new QueryWrapper<SpuCommentEntity>()
        );

        return new PageUtils(page);
    }

}