package com.ycs.gulimall.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class GulimallOrderRabbitMQConfig {
    /* 容器中的Exchange,Queue和Binding　会自动创建,在RabbitMQ不存在的情况下 */

    /**
     * 死信队列
     * @return
     */
    @Bean
    public Queue orderDelayQueue() {
        /*
            String name 队列名字
            boolean durable 是否持久化
            boolean exclusive 是否排他
            boolean autoDelete 是否自动删除
            Map<String, Object> arguments) 属性
         */
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "gulimall.order.event.exchange");
        arguments.put("x-dead-letter-routing-key", "gulimall.order.release.router.key");
        arguments.put("x-message-ttl", 60000); // 消息过期时间1分钟
        Queue queue = new Queue("gulimall.order.delay.queue", true, false, false, arguments);
        return queue;
    }

    /**
     * 普通队列
     * @return
     */
    @Bean
    public Queue orderReleaseQueue() {
        Queue queue = new Queue("gulimall.order.release.queue", true, false, false);
        return queue;
    }

    /**
     * TopicExchange
     * @return
     */
    @Bean
    public Exchange orderEventExchange() {
        /*
         *   String name　交换机名字
         *   boolean durable 是否持久化
         *   boolean autoDelete　是否自动删除
         *   Map<String, Object> arguments 属性
         * */
        return new TopicExchange("gulimall.order.event.exchange", true, false);
    }

    /**
     *　绑定死信队列
     * @return
     */
    @Bean
    public Binding orderCreateBinding() {
        /*
         * String destination 目的地（队列名或者交换机名字）
         * DestinationType destinationType 目的地类型（Queue、Exhcange）
         * String exchange 绑定的交换机
         * String routingKey　路由键
         * Map<String, Object> arguments　属性
         * */
        return new Binding("gulimall.order.delay.queue",
                Binding.DestinationType.QUEUE,
                "gulimall.order.event.exchange",
                "gulimall.order.create.router.key",
                null);
    }

    /**
     * 绑定普通队列
     * @return
     */
    @Bean
    public Binding orderReleaseBinding() {
        return new Binding("gulimall.order.release.queue",
                Binding.DestinationType.QUEUE,
                "gulimall.order.event.exchange",
                "gulimall.order.release.router.key",
                null);
    }

    /**
     * 订单释放和库存释放绑定
     * @return
     */
    @Bean
    public Binding orderReleaseOtherBinding() {
        return new Binding("gulimall.stock.release.queue",
                Binding.DestinationType.QUEUE,
                "gulimall.order.event.exchange",
                "gulimall.order.release.other.#",
                null);
    }

    /**
     * 商品秒杀队列
     * @return
     */
    @Bean
    public Queue orderSecKillOrderQueue() {
        Queue queue = new Queue("gulimall.order.seckill.queue", true, false, false);
        return queue;
    }

    /**
     * 订单释放和商品秒杀绑定
     * @return
     */
    @Bean
    public Binding orderSecKillOrderQueueBinding() {
        Binding binding = new Binding(
                "gulimall.order.seckill.queue",
                Binding.DestinationType.QUEUE,
                "gulimall.order.event.exchange",
                "gulimall.order.seckill.router.key",
                null);
        return binding;
    }
}
