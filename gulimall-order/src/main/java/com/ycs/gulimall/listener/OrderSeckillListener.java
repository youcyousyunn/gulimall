package com.ycs.gulimall.listener;

import com.rabbitmq.client.Channel;
import com.ycs.gulimall.service.OrderService;
import com.ycs.gulimall.to.mq.SeckillOrderTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@Component
@RabbitListener(queues = "gulimall.order.seckill.queue")
public class OrderSeckillListener {
    @Resource
    private OrderService orderService;

    @RabbitHandler
    public void listener(SeckillOrderTo orderTo, Channel channel, Message message) throws IOException {
        log.info("准备创建秒杀单的详细信息...");
        try {
            orderService.createSeckillOrder(orderTo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            //拒绝消费，重新入列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }

}
