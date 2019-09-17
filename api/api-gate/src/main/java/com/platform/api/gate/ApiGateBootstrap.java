package com.platform.api.gate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import com.platform.auth.client.EnableAuthClient;



/**
 * 网关
 * @version: 1.0.0
 */
// 开启基础服务
//@EnableBaseServiceClient
// 注册服务客户端
// 启用访问其他服务客户端
//@EnableFeignClients()
@EnableEurekaClient
// 启动熔断支持
//@EnableCircuitBreaker
//启用访问其他服务客户端
@EnableFeignClients(basePackages={"com.platform.api.gate.service"})
// 启动代理服务
@EnableZuulProxy
//启动认证服务客户端
@EnableAuthClient
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class ApiGateBootstrap {

    public static void main(String[] args){
        // 使用自定义的filter处理器
        //FilterProcessor.setProcessor(new GateFilterProcessor());
        SpringApplication.run(ApiGateBootstrap.class, args);
    }
}