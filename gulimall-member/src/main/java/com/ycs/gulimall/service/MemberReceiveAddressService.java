package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.MemberReceiveAddressEntity;
import com.ycs.gulimall.utils.PageUtils;

import java.util.List;
import java.util.Map;

public interface MemberReceiveAddressService extends IService<MemberReceiveAddressEntity> {
    PageUtils queryPage(Map<String, Object> params);
    List<MemberReceiveAddressEntity> getAddress(Long memberId);
}

