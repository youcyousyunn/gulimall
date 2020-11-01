package com.ycs.gulimall.feign;

import com.ycs.gulimall.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("gulimall-order")
public interface OrderFeignService {
    @GetMapping(value = "/order/status/{orderSn}")
    R getOrderStatus(@PathVariable("orderSn") String orderSn);
}
