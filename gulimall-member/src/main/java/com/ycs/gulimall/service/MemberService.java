package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.MemberEntity;
import com.ycs.gulimall.exception.RegisterException;
import com.ycs.gulimall.exception.UsernameException;
import com.ycs.gulimall.utils.PageUtils;
import com.ycs.gulimall.vo.MemberUserLoginVo;
import com.ycs.gulimall.vo.MemberUserRegisterVo;
import com.ycs.gulimall.vo.SocialUser;

import java.util.Map;

public interface MemberService extends IService<MemberEntity> {
    PageUtils queryPage(Map<String, Object> params);
    void register(MemberUserRegisterVo vo) throws RegisterException;
    MemberEntity login(MemberUserLoginVo vo);
    MemberEntity login(SocialUser socialUser) throws Exception;
    MemberEntity login(String accessTokenInfo);
}

