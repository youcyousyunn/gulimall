package com.ycs.gulimall.feign;

import com.ycs.gulimall.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("gulimall-coupon")
public interface CouponFeignService {

    /**
     * 查询最近三天需要参加秒杀商品的信息
     * @return
     */
    @GetMapping(value = "/coupon/seckillsession/last3DaysSession")
    R getLates3DaySession();
}
