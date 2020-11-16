package com.ycs.gulimall.controller;

import com.ycs.gulimall.entity.MemberEntity;
import com.ycs.gulimall.exception.BizCodeEnum;
import com.ycs.gulimall.exception.RegisterException;
import com.ycs.gulimall.service.MemberService;
import com.ycs.gulimall.utils.PageUtils;
import com.ycs.gulimall.utils.R;
import com.ycs.gulimall.vo.MemberUserLoginVo;
import com.ycs.gulimall.vo.MemberUserRegisterVo;
import com.ycs.gulimall.vo.SocialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


@RestController
@RequestMapping("member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    /**
     * 提供给别的服务进行注册
     * @param vo
     * @return
     */
    @PostMapping(value = "/register")
    public R register(@RequestBody MemberUserRegisterVo vo) {
        try {
            memberService.register(vo);
        }
        catch (RegisterException e) {
            return R.error(e.getCode(), e.getMsg());
        }

        return R.ok();
    }

    /**
     * 提供给别的服务进行登录
     * @param vo
     * @return
     */
    @PostMapping(value = "/login")
    public R login(@RequestBody MemberUserLoginVo vo) {
        MemberEntity memberEntity = memberService.login(vo);
        if (memberEntity != null) {
            return R.ok().setData(memberEntity);
        } else {
            return R.error(BizCodeEnum.LOGIN_ACCT_PASSWORD_EXCEPTION.getCode(),BizCodeEnum.LOGIN_ACCT_PASSWORD_EXCEPTION.getMessage());
        }
    }

    /**
     * 提供给别的服务进行（社交）登录
     * @param socialUser
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/oauth2/login")
    public R oauthLogin(@RequestBody SocialUser socialUser) throws Exception {
        MemberEntity memberEntity = memberService.login(socialUser);
        if (memberEntity != null) {
            return R.ok().setData(memberEntity);
        } else {
            return R.error(BizCodeEnum.LOGIN_ACCT_PASSWORD_EXCEPTION.getCode(),BizCodeEnum.LOGIN_ACCT_PASSWORD_EXCEPTION.getMessage());
        }
    }

    @PostMapping(value = "/weixin/login")
    public R weixinLogin(@RequestParam("accessTokenInfo") String accessTokenInfo) {
        MemberEntity memberEntity = memberService.login(accessTokenInfo);
        if (memberEntity != null) {
            return R.ok().setData(memberEntity);
        } else {
            return R.error(BizCodeEnum.LOGIN_ACCT_PASSWORD_EXCEPTION.getCode(),BizCodeEnum.LOGIN_ACCT_PASSWORD_EXCEPTION.getMessage());
        }
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);
        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
