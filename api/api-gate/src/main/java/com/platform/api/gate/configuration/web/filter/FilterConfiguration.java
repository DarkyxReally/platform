package com.platform.api.gate.configuration.web.filter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
     * 可重复读流的filter
     * @return
     */
    @Bean
    public ResettableRequestFilter resettableRequestFilter(){
        return new ResettableRequestFilter();
    }
}
