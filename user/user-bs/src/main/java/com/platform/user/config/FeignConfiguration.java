package com.platform.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.platform.system.common.feign.RequestTraceInterceptor;


/**
 * feign配置
 * @version: 1.0
 */
@Configuration
public class FeignConfiguration {

    /**
     * feign请求拦截器
     * 增加每次请求的唯一请求ID
     * @return
     */
    @Bean
    RequestTraceInterceptor feignRequestInterceptor(){
        return new RequestTraceInterceptor();
    }
}
