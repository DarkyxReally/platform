package com.platform.rabbitmq.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.platform.system.common.util.SnowflakeIdWorker;

/**
 * ID序列配置
 */
@Configuration
public class IdSequenceConfiguration {
    
    /**
     * 工作id
     */
    @Value("${id_sequence.worker-id}")
    private int workerId;
    
    /**
     * 数据中心id
     */
    @Value("${id_sequence.data-center-id}")
    private int dataCenterId;
    
    /**
     * Twitter_Snowflake
     * @version: 1.0
     * @return
     */
    @Bean
    public SnowflakeIdWorker snowflakeIdWorker(){
        return new SnowflakeIdWorker(workerId, dataCenterId);
    }
    
}
