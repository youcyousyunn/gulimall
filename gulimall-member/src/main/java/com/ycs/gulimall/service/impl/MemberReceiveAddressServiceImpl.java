package com.ycs.gulimall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycs.gulimall.dao.MemberReceiveAddressDao;
import com.ycs.gulimall.entity.MemberReceiveAddressEntity;
import com.ycs.gulimall.service.MemberReceiveAddressService;
import com.ycs.gulimall.utils.PageUtils;
import com.ycs.gulimall.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("memberReceiveAddressService")
public class MemberReceiveAddressServiceImpl extends ServiceImpl<MemberReceiveAddressDao, MemberReceiveAddressEntity> implements MemberReceiveAddressService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberReceiveAddressEntity> page = this.page(
                new Query<MemberReceiveAddressEntity>().getPage(params),
                new QueryWrapper<MemberReceiveAddressEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<MemberReceiveAddressEntity> getAddress(Long memberId) {
        List<MemberReceiveAddressEntity> addressList = this.baseMapper.selectList
                (new QueryWrapper<MemberReceiveAddressEntity>().eq("member_id", memberId));
        return addressList;
    }
}