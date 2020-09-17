package com.ycs.gulimall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycs.gulimall.entity.MemberLevelEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberLevelDao extends BaseMapper<MemberLevelEntity> {
    MemberLevelEntity getDefaultLevel();
}
