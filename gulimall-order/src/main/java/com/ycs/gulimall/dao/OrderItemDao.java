package com.ycs.gulimall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycs.gulimall.entity.OrderItemEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
