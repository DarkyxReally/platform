package com.platform.rabbitmq;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.platform.auth.client.EnableAuthClient;


/**
 * rabbitmq
 * @version: 1.0
 */
@SpringBootApplication(scanBasePackages={"com.platform", "com.platform.rpc.client.*.fallback"},
//禁用默认的数据源配置
exclude = {DataSourceAutoConfiguration.class})
// 注册服务客户端
@EnableEurekaClient
// 启动熔断支持
@EnableCircuitBreaker
// 启用访问其他服务客户端
@EnableFeignClients(basePackages={"com.ligu.app.rpc.client"})
// 开启事务管理
@EnableTransactionManagement
// 启用认证服务客户端
@EnableAuthClient
// 开启基础服务
//@EnableBaseServiceClient
public class RabbitMQBootstrap {

    public static void main(String[] args){
        new SpringApplicationBuilder(RabbitMQBootstrap.class).web(true).run(args);
    }
}
