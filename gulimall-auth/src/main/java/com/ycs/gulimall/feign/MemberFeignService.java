package com.ycs.gulimall.feign;

import com.ycs.gulimall.utils.R;
import com.ycs.gulimall.vo.SocialUser;
import com.ycs.gulimall.vo.UserLoginVo;
import com.ycs.gulimall.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("gulimall-member")
public interface MemberFeignService {

    @PostMapping(value = "/member/register")
    R register(@RequestBody UserRegisterVo vo);

    @PostMapping(value = "/member/login")
    R login(@RequestBody UserLoginVo vo);

    @PostMapping(value = "/member/oauth2/login")
    R oauthLogin(@RequestBody SocialUser socialUser) throws Exception;

    @PostMapping(value = "/member/weixin/login")
    R weixinLogin(@RequestParam("accessTokenInfo") String accessTokenInfo);
}
