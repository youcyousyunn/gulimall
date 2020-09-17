package com.ycs.gulimall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * nacos配置中心与Spring结合：
 * 1), 引入依赖：
 *     <dependency>
 *  *     <groupId>com.alibaba.cloud</groupId>
 *  *     <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
 *  * </dependency>
 * 2), 创建一个bootstrap.properties
 *     spring.application.name=gulimall-product
 *     spring.cloud.nacos.config.server.addr=127.0.0.1:8848
 * 3), 需要给配置中心默认添加一个叫数据集(Data Id) gulimall-product.properties
 *     默认规则：应用名.properties
 * 4), 给上一步配置添加任何配置
 * 5), 动态获取配置：
 * 　　@RefreshScope: 动态获取并刷新配置
 * 　　@Value("${配置项的名}"): 获取到配置
 * 　　如果配置中心和当前应用的配置文件都配置了同一项，优先使用配置中心的配置
 *
 * nacos命名空间与组说明：
 * 1), 命名空间(目的是配置隔离: 如各个服务的配置隔离)：
 * 　　默认：public(保留空间): 默认新增的所有配置都在这个空间
 * 　　开发，测试，生产: 利用命名空间做环境隔离
 * 　　注意: 在bootstrap.properties:配置上，需要配置使用哪个命名空间下的配置
 * 　　spring.cloud.nacos.config.namespace=67ee5d01-6af9-4aa6-a2e8-936d5f6fa67f(value必须是命名空间ID)
 * 2), 配置集：所有配置的集合
 * 3), 配置ID: 类似文件名
 * 　　Data ID:　类似文件名
 * 4), 配置分组(用来区分开发, 测试,生产环境):
 *     默认所有的配置都属于DEFAULT_GROUP
 *     一般自定义dev, test, prod组
 *
 * nacos配置中心多配置及加载
 * 1), 同时加载多个配置集(spring.cloud.nacos.config.ext-config[0])
 *    微服务任何配置信息，任何配置文件都可以放在配置中心中
 * 2), 只需要在bootstrap.properties配置文件中配置需要加载哪些配置文件即可
 * 3), 配置中心有的优先使用配置中心的
 */
@EnableRedisHttpSession     //开启springsession
@EnableCaching      //开启缓存功能
@EnableFeignClients(basePackages = "com.ycs.gulimall.feign")
@EnableDiscoveryClient
@MapperScan("com.ycs.gulimall.dao")
@SpringBootApplication //(exclude = GlobalTransactionAutoConfiguration.class)
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }
}
