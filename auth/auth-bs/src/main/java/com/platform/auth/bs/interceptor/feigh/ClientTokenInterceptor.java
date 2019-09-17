package com.platform.auth.bs.interceptor.feigh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.platform.auth.common.config.AuthClientConfig;
import com.platform.auth.model.model.response.Token;
import com.platform.auth.service.AuthClientService;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * feign客户端请求Token拦截器
 * @version: 1.0
 */
public class ClientTokenInterceptor implements RequestInterceptor {

    private Logger logger = LoggerFactory.getLogger(ClientTokenInterceptor.class);

    @Autowired
    private AuthClientConfig clientConfig;

    @Autowired
    private AuthClientService authClientService;

    @Override
    public void apply(RequestTemplate requestTemplate){
    	logger.info("进入feign客户端请求Token拦截器");
        try{
            // 服务间认证token
            Token token = authClientService.apply(clientConfig.getClientId(),
                    clientConfig.getSecret());
            requestTemplate.header(clientConfig.getTokenHeader(), token.getAccessToken());
        }catch(Exception e){
            logger.error("服务调用拦截器增加客户端认证请求头异常:" + e.getMessage(), e);
        }
    }
}
