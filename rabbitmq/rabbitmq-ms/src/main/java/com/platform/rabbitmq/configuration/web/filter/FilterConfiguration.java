package com.platform.rabbitmq.configuration.web.filter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.platform.auth.common.userdetail.IUserDetailService;
import com.platform.auth.common.web.filter.UserRequestTraceFilter;
import com.platform.system.common.web.filter.RequestTraceFilter;
import com.platform.system.common.web.filter.ResettableRequestFilter;


/**
 * 公共配置
 */
@Configuration
public class FilterConfiguration {

    /**
     * 请求溯源filter
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RequestTraceFilter requestTraceFilter(){
        return new RequestTraceFilter();
    }
    
    /**
     * 用户溯源
     * @version: 
     * @param userDetailService
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public UserRequestTraceFilter userRequestTraceFilter(IUserDetailService userDetailService){
        return new UserRequestTraceFilter(userDetailService);
    }

    /**
     * 可重复读流的filter
     * @return
     */
    @Bean
    public ResettableRequestFilter resettableRequestFilter(){
        return new ResettableRequestFilter();
    }

}
