package com.platform.auth.bs.configuration;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.platform.system.common.web.RequestDataLogInterceptor;

import lombok.extern.slf4j.Slf4j;


/**
 * web安全性配置
 * @version: 1.0
 */
@Configuration("authServerWebConfig")
@Primary
@Slf4j
public class WebConfiguration extends WebMvcConfigurerAdapter {

    /**
     * 过滤认证路径
     * @return
     */
    private ArrayList<String> excludePathPatterns(){
        ArrayList<String> list = new ArrayList<>();
        String[] urls = {"/auth/wechat/**","/auth/file/**"};
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
    	log.info("开始web安全性配置");
        ArrayList<String> commonPathPatterns = excludePathPatterns();
        registry.addInterceptor(requestDataLogInterceptor).addPathPatterns("/**").excludePathPatterns(commonPathPatterns.toArray(new String[]{}));
        if(ArrayUtils.isNotEmpty(interceptors)){
            for(HandlerInterceptor handlerInterceptor : interceptors){
                registry.addInterceptor(handlerInterceptor).addPathPatterns("/**").excludePathPatterns(commonPathPatterns.toArray(new String[]{}));
            }
        }
    }
}
