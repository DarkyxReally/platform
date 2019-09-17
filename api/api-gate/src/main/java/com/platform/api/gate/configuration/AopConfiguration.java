package com.platform.api.gate.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.platform.system.common.aspect.HibernateValidatorAspect;
import com.platform.system.common.aspect.TraceAspect;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.trace.TraceProperties;


/**
 * 切面配置
 * @version: 1.0
 */
@Configuration
@EnableConfigurationProperties({TraceProperties.class})
public class AopConfiguration {

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
}
