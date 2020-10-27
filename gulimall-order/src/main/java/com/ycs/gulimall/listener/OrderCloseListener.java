package com.ycs.gulimall.listener;

import com.rabbitmq.client.Channel;
import com.ycs.gulimall.entity.OrderEntity;
import com.ycs.gulimall.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 *　定时关闭订单
 **/
@Slf4j
@RabbitListener(queues = "gulimall.order.release.queue")
@Service
public class OrderCloseListener {
    @Resource
    private OrderService orderService;


    @RabbitHandler
    public void handleOrderClose(OrderEntity orderEntity, Channel channel, Message message) throws IOException {
        log.info("收到已过期的订单，准备关闭订单信息: {}", orderEntity.toString());
        try {
            orderService.closeOrder(orderEntity);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }
}
