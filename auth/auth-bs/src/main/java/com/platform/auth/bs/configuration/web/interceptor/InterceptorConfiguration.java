package com.platform.auth.bs.configuration.web.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.platform.auth.bs.interceptor.web.OperatorLogRestInterceptor;
//import com.platform.auth.bs.interceptor.web.OperatorLogRestInterceptor;
import com.platform.auth.bs.interceptor.web.ServiceAuthRestInterceptor;
import com.platform.auth.bs.interceptor.web.UserAuthRestInterceptor;
//import com.platform.auth.bs.interceptor.web.UserAuthRestInterceptor;
import com.platform.auth.common.userdetail.IUserDetailService;
import com.platform.auth.common.userdetail.UserDetailServiceImpl;
//import com.platform.system.common.web.intercerpor.CheckXssInterceptor;

import lombok.extern.slf4j.Slf4j;


/**
 * 拦截器配置
 * @version: 1.0
 */
@Configuration("authMsInterceptorConfiguration")
@Slf4j
public class InterceptorConfiguration {

    /**
     * 服务间认证拦截器
     * @return
     */
    @Bean
    ServiceAuthRestInterceptor serviceAuthRestInterceptor(){
    	log.info("===========> 服务间认证拦截器");
        return new ServiceAuthRestInterceptor();
    }

    /**
     * 用户详情实现类
     * @return
     */
    @Bean
    IUserDetailService userDetailServiceImpl(){
    	log.info("===========>  用户详情实现类");
        return new UserDetailServiceImpl();
    }
    
    /**
     * 用户认证拦截器
     * @return
     */
    @Bean
    UserAuthRestInterceptor userAuthRestInterceptor() {
    	log.info("===========>  用户认证拦截器");
        return new UserAuthRestInterceptor();
    }
    
    /**
     * REST请求日志拦截器
     * @return
     */
    @Bean
    OperatorLogRestInterceptor operatorLogRestInterceptor() {
    	log.info("===========>  REST请求日志拦截器");
        return new OperatorLogRestInterceptor();
    }

    /**
     * XSS拦截器
     * @return
     */
//    @Bean
//    CheckXssInterceptor checkXssInterceptor() {
//        return new CheckXssInterceptor();
//    }
}
