package com.platform.user.config;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.platform.system.common.web.RequestDataLogInterceptor;


/**
 * WEB配置类
 * @version: 1.0
 */
@Configuration("userWebMvcConfig")
@Primary
public class WebConfiguration extends WebMvcConfigurerAdapter {
    
    /**
     * 不拦截的地址
     * @return
     */
    private ArrayList<String> excludePathPatterns(){
        ArrayList<String> list = new ArrayList<>();
        String[] urls = {"/wechat/user/**", "/web/**"};
        Collections.addAll(list, urls);
        return list;
    }

    /**
     * 请求数据日志拦截器
     * @return
     */
    @Bean
    RequestDataLogInterceptor requestDataLogInterceptor(){
        return new RequestDataLogInterceptor();
    }
    
    /**
     * 拦截器
     */
    @Autowired
    private HandlerInterceptor[] interceptors;
    
    @Autowired
    private RequestDataLogInterceptor requestDataLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        ArrayList<String> commonPathPatterns = excludePathPatterns();
        registry.addInterceptor(requestDataLogInterceptor).addPathPatterns("/**")
        .excludePathPatterns(commonPathPatterns.toArray(new String[]{}));
        if (ArrayUtils.isNotEmpty(interceptors)){
            for(HandlerInterceptor handlerInterceptor : interceptors){
                registry.addInterceptor(handlerInterceptor).addPathPatterns("/**")
                .excludePathPatterns(commonPathPatterns.toArray(new String[]{}));
            }
        }
    }
}
