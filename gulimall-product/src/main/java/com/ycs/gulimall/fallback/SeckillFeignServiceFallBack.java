package com.ycs.gulimall.fallback;

import com.ycs.gulimall.exception.BizCodeEnum;
import com.ycs.gulimall.feign.SeckillFeignService;
import com.ycs.gulimall.utils.R;
import org.springframework.stereotype.Component;

@Component
public class SeckillFeignServiceFallBack implements SeckillFeignService {
    @Override
    public R getSkuSeckilInfo(Long skuId) {
        return R.error(BizCodeEnum.TO_MANY_REQUEST.getCode(), BizCodeEnum.TO_MANY_REQUEST.getMessage());
    }
}
