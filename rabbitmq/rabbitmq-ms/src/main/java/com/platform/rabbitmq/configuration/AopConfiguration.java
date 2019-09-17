package com.platform.rabbitmq.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.platform.system.common.aspect.DelayReturnAspect;
import com.platform.system.common.aspect.HibernateValidatorAspect;
import com.platform.system.common.aspect.ManualExceptionAspect;
import com.platform.system.common.aspect.RequestIdStuffAspect;
import com.platform.system.common.aspect.RequestLoggingAspect;
import com.platform.system.common.aspect.TraceAspect;
import com.platform.system.common.config.properties.DelayProperties;
import com.platform.system.common.config.properties.ManualExceptionProperties;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.trace.TraceProperties;


/**
 * 切面配置
 * @version: 1.0
 */
@Configuration
@EnableConfigurationProperties({DelayProperties.class, ManualExceptionProperties.class, TraceProperties.class})
public class AopConfiguration {

    /***
     * 请求ID切面
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RequestIdStuffAspect idStuffAspect() {
        final int order = Byte.MAX_VALUE;
        return new RequestIdStuffAspect(order);
    }

    /**
     * 日志记录切面
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RequestLoggingAspect logsAspect() {
        final int order = Byte.MAX_VALUE + 1;
        return new RequestLoggingAspect(order);
    }

    /**
     * hibernate校验框架切面
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public HibernateValidatorAspect hibernateValidatorAspect() {
        final int order = Byte.MAX_VALUE + 2;
        return new HibernateValidatorAspect(order, StatusCode.INVALID_MODEL_FIELDS);
    }
    
    
    /**
     * 溯源切面
     * @param properties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public TraceAspect traceAspect(TraceProperties properties) {
        final int order = Byte.MAX_VALUE + 3;
        return new TraceAspect(order, properties);
    }
    
    /**
     * 延迟返回切面
     * @param properties
     * @return
     */
    @Bean
    public DelayReturnAspect delayReturnAspect(DelayProperties properties) {
        return new DelayReturnAspect(Ordered.LOWEST_PRECEDENCE, properties);
    }

    /**
     * 手动抛出异常切面
     * @param properties
     * @return
     */
    @Bean
    public ManualExceptionAspect manualExceptionAspect(ManualExceptionProperties properties) {
        return new ManualExceptionAspect(Ordered.LOWEST_PRECEDENCE - 1, properties);
    }

}
