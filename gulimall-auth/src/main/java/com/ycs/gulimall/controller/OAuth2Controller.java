package com.ycs.gulimall.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ycs.gulimall.feign.MemberFeignService;
import com.ycs.gulimall.utils.HttpUtils;
import com.ycs.gulimall.utils.R;
import com.ycs.gulimall.vo.MemberResponseVo;
import com.ycs.gulimall.vo.SocialUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.ycs.gulimall.constant.AuthConstant.LOGIN_USER;


@Slf4j
@Controller
public class OAuth2Controller {
    @Autowired
    private MemberFeignService memberFeignService;


    /**
     * 微博社交登录成功回调
     * @param code
     * @param session
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/oauth2.0/weibo/success")
    public String weiboAuth(@RequestParam("code") String code, HttpSession session) throws Exception {
        //1,根据code换取access_token
        Map<String, String> map = new HashMap<>();
        map.put("client_id", "1049326439");
        map.put("client_secret", "3e01442e74458ae6b2b634cb3d2d38e5");
        map.put("grant_type", "authorization_code");
        map.put("redirect_uri", "http://www.auth.gulimall.com/oauth2.0/weibo/success");
        map.put("code", code);
        HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post", new HashMap<>(), map, new HashMap<>());

        //2,处理
        if (response.getStatusLine().getStatusCode() == 200) {
            //获取到了access_token
            String json = EntityUtils.toString(response.getEntity());
            SocialUser socialUser = JSON.parseObject(json, SocialUser.class);
            //调用远程服务
            R oauthLogin = memberFeignService.oauthLogin(socialUser);
            if (oauthLogin.getCode() == 0) {
                MemberResponseVo data = oauthLogin.getData("data", new TypeReference<MemberResponseVo>() {});
                log.info("登录成功：用户信息：{}",data.toString());

                //1、第一次使用session，命令浏览器保存卡号，JSESSIONID这个cookie
                //以后浏览器访问哪个网站就会带上这个网站的cookie
                //TODO 1、默认发的令牌。当前域（解决子域session共享问题）
                //TODO 2、使用JSON的序列化方式来序列化对象到Redis中
                session.setAttribute(LOGIN_USER,data);
                
                //2、登录成功跳回首页
                return "redirect:http://www.gulimall.com";
            } else {
                return "redirect:http://www.auth.gulimall.com/login.html";
            }
        } else {
            return "redirect:http://www.auth.gulimall.com/login.html";
        }
    }

}
