package com.ycs.gulimall.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


@Configuration
public class GulimallRabbitMQConfig {

    /**
     * 使用JSON序列化机制进行消息转换
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // @RabbitListener(queues = "gulimall.stock.release.queue")
    // public void handle(Message message) {
    //
    // }

    /**
     * 库存服务默认的交换机
     * @return
     */
    @Bean
    public Exchange stockEventExchange() {
        TopicExchange topicExchange = new TopicExchange("gulimall.stock.event.exchange", true, false);
        return topicExchange;
    }

    /**
     * 普通队列
     * @return
     */
    @Bean
    public Queue stockReleaseStockQueue() {
        Queue queue = new Queue("gulimall.stock.release.queue", true, false, false);
        return queue;
    }

    /**
     * 延迟队列
     * @return
     */
    @Bean
    public Queue stockDelay() {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "stock-event-exchange");
        arguments.put("x-dead-letter-routing-key", "stock.release");
        // 消息过期时间 2分钟
        arguments.put("x-message-ttl", 120000);
        Queue queue = new Queue("gulimall.stock.delay.queue", true, false, false,arguments);
        return queue;
    }

    /**
     * 交换机与普通队列绑定
     * @return
     */
    @Bean
    public Binding stockLocked() {
        Binding binding = new Binding("gulimall.stock.release.queue",
                Binding.DestinationType.QUEUE,
                "gulimall.stock.event.exchange",
                "gulimall.stock.release.#",
                null);
        return binding;
    }

    /**
     * 交换机与延迟队列绑定
     * @return
     */
    @Bean
    public Binding stockLockedBinding() {
        return new Binding("gulimall.stock.delay.queue",
                Binding.DestinationType.QUEUE,
                "gulimall.stock.event.exchange",
                "gulimall.stock.locked",
                null);
    }
}
