package com.platform.auth.bs.configuration.web.filter;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.platform.auth.common.userdetail.IUserDetailService;
import com.platform.auth.common.userdetail.UserDetailServiceImpl;
import com.platform.auth.common.web.filter.UserRequestTraceFilter;
import com.platform.system.common.web.filter.RequestTraceFilter;
import com.platform.system.common.web.filter.ResettableRequestFilter;


/**
 * 公共配置
 */
@Configuration
@Slf4j
public class FilterConfiguration {
    
    /**
     * 用户信息详情
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public IUserDetailService userDetailServiceImpl(){
    	log.info("===========>  公共配置--- 用户信息详情");
        return new UserDetailServiceImpl();
    }
    
    /**
     * 请求溯源filter
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RequestTraceFilter requestTraceFilter(){
    	log.info("===========>  公共配置--- 请求溯源filter");
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
    	log.info("===========>  公共配置---用户溯源");
        return new UserRequestTraceFilter(userDetailService);
    }
    
    /**
     * 可重复读流的filter
     * @return
     */
    @Bean
    public ResettableRequestFilter resettableRequestFilter() {
    	log.info("===========>  公共配置---可重复读流的filter");
        return new ResettableRequestFilter();
    }

}
