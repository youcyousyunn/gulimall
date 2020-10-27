package com.ycs.gulimall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ycs.gulimall.entity.OrderEntity;
import com.ycs.gulimall.to.mq.SeckillOrderTo;
import com.ycs.gulimall.utils.PageUtils;
import com.ycs.gulimall.vo.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface OrderService extends IService<OrderEntity> {
    OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;
    PageUtils queryPage(Map<String, Object> params);
    SubmitOrderResponseVo submitOrder(OrderSubmitVo vo);

    /**
     * 按照订单号获取订单信息
     * @param orderSn
     * @return
     */
    OrderEntity getOrderByOrderSn(String orderSn);
    void closeOrder(OrderEntity orderEntity);

    /**
     * 获取当前订单的支付信息
     * @param orderSn
     * @return
     */
    PayVo getOrderPay(String orderSn);

    /**
     * 查询当前用户所有订单数据
     * @param params
     * @return
     */
    PageUtils queryPageWithItem(Map<String, Object> params);

    /**
     *支付宝异步通知处理订单数据
     * @param asyncVo
     * @return
     */
    String handlePayResult(PayAsyncVo asyncVo);

    /**
     * 微信异步通知处理
     * @param notifyData
     */
    String asyncNotify(String notifyData);

    /**
     * 创建秒杀单
     * @param orderTo
     */
    void createSeckillOrder(SeckillOrderTo orderTo);
}

