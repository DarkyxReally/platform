package com.platform.auth.client.configuration;

import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.platform.auth.client.jwt.ServiceAuthUtil;
import com.platform.auth.client.jwt.UserAuthUtil;
import com.platform.auth.common.config.AuthClientConfig;
import com.platform.auth.common.config.AuthUserConfig;


/**
 * 自动配置类
 * @version: 1.0
 */
@Configuration("authAutoConfig")
@ComponentScan({"com.platform.auth.client"})
@EnableFeignClients(basePackages={"com.platform.auth.client.rpc.client"})
@RemoteApplicationEventScan(basePackages = "com.platform.auth.common.event")
public class AutoConfiguration {

    /**
     * 客户端认证配置的bean名称
     */
    public static final String BEAN_NAME_CLIENT_CONFIG = "defaultAuthClientConfig";
    /**
     * 用户认证配置的bean名称
     */
    public static final String BEAN_NAME_USER_CONFIG = "defaultAuthUserConfig";
    /**
     * 客户端认证工具类bean名称
     */
    public static final String BEAN_NAME_CLIENT_AUTH_UTIL = "defaultServiceAuthUtil";
    /**
     * 用户认证工具类bean名称
     */
    public static final String BEAN_NAME_USER_AUTH_UTIL = "defaultUserAuthUtil";
    
    
    /**
     * 客户端认证配置
     * @return
     */
    @Bean(AutoConfiguration.BEAN_NAME_CLIENT_CONFIG)
    AuthClientConfig authClientConfig(){
        return new AuthClientConfig();
    }

    /**
     * 用户认证配置
     * @return
     */
    @Bean(AutoConfiguration.BEAN_NAME_USER_CONFIG)
    AuthUserConfig authUserConfig(){
        return new AuthUserConfig();
    }
    
    /**
     * 服务认证工具类
     * @return
     */
    @Bean(AutoConfiguration.BEAN_NAME_CLIENT_AUTH_UTIL)
    ServiceAuthUtil serviceAuthUtil(){
        return new ServiceAuthUtil();
    }
    
    /**
     * 用户认证工具类
     * @return
     */
    @Bean(AutoConfiguration.BEAN_NAME_USER_AUTH_UTIL)
    UserAuthUtil userAuthUtil(){
        return new UserAuthUtil();
    }
}

