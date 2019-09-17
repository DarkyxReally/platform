package com.platform.auth.client.interceptor.feign;

import org.springframework.beans.factory.annotation.Autowired;

import com.platform.auth.client.jwt.ServiceAuthUtil;
import com.platform.auth.common.config.AuthClientConfig;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 服务请求拦截器
 * @version: 1.0
 */
public class ServiceFeignInterceptor implements RequestInterceptor {

    @Autowired
    private AuthClientConfig clientConfig;
    @Autowired
    private ServiceAuthUtil serviceAuthUtil;

    public ServiceFeignInterceptor() {}

    @Override
    public void apply(RequestTemplate requestTemplate){
        requestTemplate.header(clientConfig.getTokenHeader(), serviceAuthUtil.getClientToken());
        requestTemplate.header("client-version", "1.0");
    }
    
    public void setClientConfig(AuthClientConfig clientConfig){
        this.clientConfig = clientConfig;
    }

    public void setServiceAuthUtil(ServiceAuthUtil serviceAuthUtil){
        this.serviceAuthUtil = serviceAuthUtil;
    }
}