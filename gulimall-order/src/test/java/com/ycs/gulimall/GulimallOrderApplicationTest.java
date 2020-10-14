package com.ycs.gulimall;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallOrderApplicationTest {
    @Autowired
    private AmqpAdmin amqpAdmin;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test
    public void sendMsg() {
        String msg = "这是谷粒商城一则订单测试消息!";
        rabbitTemplate.convertAndSend("gulimall.order.test.exchange", "gulimall.order.test.routeKey", msg);
        log.info("订单异步消息发送完成！");
    }

    @Test
    public void createExchange() {
        Exchange exchange = new DirectExchange("gulimall.order.test.exchange", true, false);
        amqpAdmin.declareExchange(exchange);
        log.info("创建direct exchange完成！");
    }

    @Test
    public void createQueue() {
        Queue queue = new Queue("gulimall.order.test.queue", true, false, false);
        amqpAdmin.declareQueue(queue);
        log.info("创建Queue完成！");
    }

    @Test
    public void createBinding() {
        Binding binding = new Binding("gulimall.order.test.queue",
                Binding.DestinationType.QUEUE,
                "gulimall.order.test.exchange",
                "gulimall.order.test.routeKey",null);
        amqpAdmin.declareBinding(binding);
        log.info("direct exchange与Queue绑定完成！");
    }
}
