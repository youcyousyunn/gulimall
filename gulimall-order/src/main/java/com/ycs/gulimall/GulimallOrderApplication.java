package com.ycs.gulimall;

import com.alibaba.cloud.seata.GlobalTransactionAutoConfiguration;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 使用RabbitMQ
 * 1, 引入amqp场景，RabbitAutoConfiguration就会自动生效
 * 2, 给容器中自动配置了
 *    RabbitTemplate,AmqpAdmin,CachingConnectionFactory,RabbitMessagingTemplate
 *    所有属性都是 spring.rabbitmq
 *    @ConfigurationProperties(prefix = "spring.rabbitmq")
 *    public class RabbitProperties
 *
 * 3, 在配置文件中配置spring.rabbitmq信息
 * 4, @EnableRabbit: @EnableXXX　开启功能
 * 5, @RabbitListener: 监听队列消息(前提必须有@EnableRabbit)
 *    @RabbitListener: 类+方法上(监听哪些消息队列)
 *    @RabbitHandler: 标在方法上(重载区分接收不同类型的消息)
 */
@EnableAspectJAutoProxy(exposeProxy = true)     //开启了aspect动态代理模式,对外暴露代理对象
@EnableRedisHttpSession     //开启springsession
@EnableRabbit
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(exclude = GlobalTransactionAutoConfiguration.class)
public class GulimallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallOrderApplication.class, args);
    }
}
