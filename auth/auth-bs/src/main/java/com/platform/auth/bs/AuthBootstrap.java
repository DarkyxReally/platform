package com.platform.auth.bs;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *   认证模块
 * @version: 1.0
 */
@SpringBootApplication(scanBasePackages={"com.platform"})
// 注册服务客户端
@EnableEurekaClient
//druid监控页面
@ServletComponentScan("com.platform.auth.bs.configuration.druid")
//启用访问其他服务客户端
@EnableFeignClients(basePackages={"com.platform.user.client"})
//开启事务管理
@EnableTransactionManagement
public class AuthBootstrap {

    public static void main(String[] args){
        new SpringApplicationBuilder(AuthBootstrap.class).web(true).run(args);
    }
}
