package com.platform.system.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 *	 注册中心
 * @version: 1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class CenterBootstrap {
	public static void main(String[] args){
        SpringApplication.run(CenterBootstrap.class, args);
    }
}
