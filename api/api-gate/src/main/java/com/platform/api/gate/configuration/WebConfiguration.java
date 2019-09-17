package com.platform.api.gate.configuration;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.platform.system.common.web.RequestDataLogInterceptor;


/**
 * WEB配置类
 * @version: 1.0
 */
@Configuration("apiGateWebMvcConfig")
@Primary
public class WebConfiguration extends WebMvcConfigurerAdapter {

    /**
     * 不拦截的地址
     * @return
     */
    private ArrayList<String> excludePathPatterns(){
        ArrayList<String> list = new ArrayList<>();
        String[] urls = {"/static/**"};
        Collections.addAll(list, urls);
        return list;
    }

    @Bean
    RequestDataLogInterceptor getRequestDataLogInterceptor(){
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
        registry.addInterceptor(requestDataLogInterceptor).addPathPatterns("/**").excludePathPatterns(commonPathPatterns.toArray(new String[]{}));
        if(ArrayUtils.isNotEmpty(interceptors)){
            for(HandlerInterceptor handlerInterceptor : interceptors){
                registry.addInterceptor(handlerInterceptor).addPathPatterns("/**").excludePathPatterns(commonPathPatterns.toArray(new String[]{}));
            }
        }
    }

    @Bean
    InternalResourceViewResolver internalResourceViewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        // redirectHttp10Compatible:解决https环境下使用redirect重定向地址变为http的协议，无法访问服务的问题
        // 关闭了对http1.0协议的兼容支持
        viewResolver.setRedirectHttp10Compatible(false);
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        // 静态资源处理
        registry.addResourceHandler("/h5/css/**").addResourceLocations("classpath:static/css/");
        registry.addResourceHandler("/h5/js/**").addResourceLocations("classpath:static/js/");
        registry.addResourceHandler("/h5/font/**").addResourceLocations("classpath:static/font/");
        registry.addResourceHandler("/h5/music/**").addResourceLocations("classpath:static/music/");
        registry.addResourceHandler("/h5/images/**").addResourceLocations("classpath:static/images/");
        registry.addResourceHandler("/h5/plugins/**").addResourceLocations("classpath:static/plugins/");
        super.addResourceHandlers(registry);
    }

}
