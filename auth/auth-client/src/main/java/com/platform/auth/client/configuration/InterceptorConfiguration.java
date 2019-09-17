package com.platform.auth.client.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.platform.auth.client.interceptor.web.OperatorLogRestInterceptor;
import com.platform.auth.client.interceptor.web.ServiceAuthRestInterceptor;
import com.platform.auth.client.interceptor.web.UserAuthRestInterceptor;
import com.platform.auth.client.util.RequestDataLogServiceImpl;
import com.platform.auth.common.userdetail.IUserDetailService;
import com.platform.auth.common.userdetail.UserDetailServiceImpl;
import com.platform.auth.common.util.IRequestDataLogService;
import com.platform.system.common.web.intercerpor.CheckXssInterceptor;


/**
 * 拦截器配置
 * @version: 1.0
 */
@Configuration("authInterceptorConfiguration")
public class InterceptorConfiguration {

    /**
     * 服务间认证拦截器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "auth.client", name = "service-auth", havingValue = "true")
    ServiceAuthRestInterceptor serviceAuthRestInterceptor(){
        return new ServiceAuthRestInterceptor();
    }
    
    /**
     * 用户详情实现类
     * @version: 1.0
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    IUserDetailService userDetailService(){
        return new UserDetailServiceImpl();
    }
    
    /**
     * 用户认证拦截器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "auth.client", name = "user-auth", havingValue = "true")
    UserAuthRestInterceptor userAuthRestInterceptor() {
        return new UserAuthRestInterceptor();
    }
    
    /**
     * 请求日志保存
     * @version: 1.0
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    IRequestDataLogService requestDataLogService(){
        return new RequestDataLogServiceImpl();
    }
    
    /**
     * REST请求日志拦截器
     * @version: 1.0
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "auth.client", name = "rest-log", havingValue = "true")
    OperatorLogRestInterceptor operatorLogRestInterceptor() {
        return new OperatorLogRestInterceptor();
    }

    /**
     * XSS拦截器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    CheckXssInterceptor checkXssInterceptor() {
        return new CheckXssInterceptor();
    }
    

}
