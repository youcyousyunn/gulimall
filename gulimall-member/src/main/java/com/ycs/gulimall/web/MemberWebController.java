package com.ycs.gulimall.web;

import com.ycs.gulimall.feign.OrderFeignService;
import com.ycs.gulimall.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("member")
public class MemberWebController {
    @Autowired
    private OrderFeignService orderFeignService;


    /**
     * 获取用户订单列表
     * @param pageNum
     * @param model
     * @param request
     * @return
     */
    @GetMapping(value = "/order.html")
    public String memberOrderPage(@RequestParam(value = "pageNum",required = false,defaultValue = "0") Integer pageNum,
                                  Model model, HttpServletRequest request) {
        //查出当前登录用户的所有订单列表数据
        Map<String,Object> page = new HashMap<>();
        page.put("page",pageNum.toString());

        //远程查询订单服务订单数据
        R r = orderFeignService.listWithItem(page);
        model.addAttribute("orders", r);
        return "orderList";
    }
}
