package com.platform.auth.bs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.platform.auth.bs.interceptor.feigh.ClientTokenInterceptor;

import lombok.extern.slf4j.Slf4j;


/**
 * feign配置
 * @version: 1.0
 */
@Configuration
@Slf4j
public class FeignConfiguration {

    /**
     * 客户端token拦截器
     * @return
     */
    @Bean
    ClientTokenInterceptor getClientTokenInterceptor(){
    	log.info("===========> 开启feign配置");
        return new ClientTokenInterceptor();
    }
}
