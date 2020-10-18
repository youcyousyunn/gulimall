package com.ycs.gulimall.web;

import com.ycs.gulimall.exception.BizCodeEnum;
import com.ycs.gulimall.exception.NoStockException;
import com.ycs.gulimall.service.OrderService;
import com.ycs.gulimall.vo.OrderConfirmVo;
import com.ycs.gulimall.vo.OrderSubmitVo;
import com.ycs.gulimall.vo.SubmitOrderResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

@Controller
public class OrderWebController {

    @Autowired
    private OrderService orderService;

    /**
     * 去订单结算确认页
     * @param model
     * @param request
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping(value = "/toTrade")
    public String toTrade(Model model, HttpServletRequest request) throws ExecutionException, InterruptedException {
        //订单确认页返回需要用的数据
        OrderConfirmVo confirmVo = orderService.confirmOrder();
        model.addAttribute("confirmOrderData",confirmVo);
        //展示订单确认的数据
        return "confirm";
    }

    /**
     * 提交订单
     * @param vo
     * @return
     */
    @PostMapping(value = "/submitOrder")
    public String submitOrder(OrderSubmitVo vo, Model model, RedirectAttributes attributes) {
        try {
            SubmitOrderResponseVo responseVo = orderService.submitOrder(vo);
            //下单成功来到支付选择页
            //下单失败回到订单确认页重新确定订单信息
            if (responseVo.getCode() == 0) {
                //成功
                model.addAttribute("submitOrderResp",responseVo);
                return "pay";
            } else {
                Integer code = responseVo.getCode();
                String msg = "下单失败，";
                if(code.equals(BizCodeEnum.ORDER_TOKEN_EXPIRED.getCode())) {
                    msg += "订单令牌信息过期，请刷新页面再次提交";
                } else if(code.equals(BizCodeEnum.ORDER_PRODUCT_PRICE_CHANGED.getCode())) {
                    msg += "订单商品价格发生变化，请确认后再次提交";
                } else if(code.equals(BizCodeEnum.NO_STOCK_EXCEPTION.getCode())) {
                    msg += "库存锁定失败，商品库存不足";
                }
                attributes.addFlashAttribute("msg", msg);
                return "redirect:http://www.order.gulimall.com/toTrade";
            }
        } catch (Exception e) {
            if (e instanceof NoStockException) {
                String message = ((NoStockException)e).getMessage();
                attributes.addFlashAttribute("msg", message);
            }
            return "redirect:http://www.order.gulimall.com/toTrade";
        }
    }

}
