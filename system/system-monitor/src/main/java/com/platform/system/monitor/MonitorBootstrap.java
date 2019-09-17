package com.platform.system.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;



/**
 * 监控服务
 * 熔断机制
 * @version: 1.0.0
 */
// 启用访问其他服务客户端
//@EnableFeignClients(basePackages={"com.ligu.app.rpc.client"})
// session管理
//@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE, redisNamespace = "lgapp:monitor")
// 启动熔断支持
//@EnableCircuitBreaker
// Hystrix支持
//@EnableHystrix
// Hystrix Dashboard支持
//@EnableHystrixDashboard
// SBA管理监控端
//@EnableAdminServer
// 注册服务客户端
@EnableEurekaClient
@SpringBootApplication
public class MonitorBootstrap {

    public static void main(String[] args){
        SpringApplication.run(MonitorBootstrap.class, args);
    }

//    @Bean
//    public HystrixCommandAspect hystrixAspect(){
//        return new HystrixCommandAspect();
//    }
}
