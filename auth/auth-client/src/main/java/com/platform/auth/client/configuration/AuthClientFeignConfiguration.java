package com.platform.auth.client.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.platform.auth.client.interceptor.feign.AuthClientVersionFeignInterceptor;
import com.platform.auth.client.interceptor.feign.ServiceFeignInterceptor;
import com.platform.auth.client.jwt.ServiceAuthUtil;
import com.platform.auth.common.config.AuthClientConfig;


/** Feign配置
 * 
 * @version: 1.0 */
@Configuration
public class AuthClientFeignConfiguration {

    /**
     * 客户端版本号认证拦截
     * @version: 
     * @return
     */
    @Bean
    public AuthClientVersionFeignInterceptor authClientVersionFeignInterceptor() {
        return new AuthClientVersionFeignInterceptor();
    }

    /**
     * feign服务间认证拦截
     * @param clientConfig
     * @param serviceAuthUtil
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    ServiceFeignInterceptor initServieInterceptor(AuthClientConfig clientConfig, ServiceAuthUtil serviceAuthUtil) {
        ServiceFeignInterceptor serviceFeignInterceptor = new ServiceFeignInterceptor();
        serviceFeignInterceptor.setClientConfig(clientConfig);
        serviceFeignInterceptor.setServiceAuthUtil(serviceAuthUtil);
        return serviceFeignInterceptor;
    }
}
