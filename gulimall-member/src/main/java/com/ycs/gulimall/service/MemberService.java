package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.MemberEntity;
import com.ycs.gulimall.exception.PhoneException;
import com.ycs.gulimall.exception.UsernameException;
import com.ycs.gulimall.utils.PageUtils;
import com.ycs.gulimall.vo.MemberUserLoginVo;
import com.ycs.gulimall.vo.MemberUserRegisterVo;
import com.ycs.gulimall.vo.SocialUser;

import java.util.Map;

public interface MemberService extends IService<MemberEntity> {
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 用户注册
     * @param vo
     */
    void register(MemberUserRegisterVo vo);

    /**
     * 判断邮箱是否重复
     * @param phone
     * @return
     */
    void checkPhoneUnique(String phone) throws PhoneException;

    /**
     * 判断用户名是否重复
     * @param userName
     * @return
     */
    void checkUserNameUnique(String userName) throws UsernameException;

    /**
     * 用户登录
     * @param vo
     * @return
     */
    MemberEntity login(MemberUserLoginVo vo);

    /**
     * 社交用户的登录
     * @param socialUser
     * @return
     */
    MemberEntity login(SocialUser socialUser) throws Exception;

    /**
     * 微信登录
     * @param accessTokenInfo
     * @return
     */
    MemberEntity login(String accessTokenInfo);
}

