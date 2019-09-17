package com.platform.auth.client.interceptor.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 认证客户端版本号请求拦截器
 * @version:1.0
 */
public class AuthClientVersionFeignInterceptor implements RequestInterceptor {

    public AuthClientVersionFeignInterceptor() {}

    @Override
    public void apply(RequestTemplate requestTemplate){
        requestTemplate.header("client-version", "1.0");
    }
}