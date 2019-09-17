package com.platform.user;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.platform.auth.client.EnableAuthClient;

/**
 * 用户管理后台
 * @author: 李文斌
 * @since: 2019年8月26日上午10:48:37
 * @version: 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.platform.user"})
//注册服务客户端
@EnableEurekaClient
//开启异步
@EnableAsync
//开启事务管理
@EnableTransactionManagement
@EnableFeignClients
//启用认证客户端
@EnableAuthClient
public class UserBootstrap {
	
	public static void main(String[] args){
        new SpringApplicationBuilder(UserBootstrap.class).web(true).run(args);
    }

}
