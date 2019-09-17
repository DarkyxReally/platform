package com.platform.rabbitmq.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.platform.rabbitmq.config.JPushConfig;
import com.platform.rabbitmq.config.RabbitMQConfig;



/**
 * 配置类
 * @version: 1.0
 */
@Configuration
public class ConfigConfiguration {

    /**
     * 极光推送配置
     * @return
     */
    @Bean
    public JPushConfig jPushConfig(){
        return new JPushConfig();
    }
    
    /**
     * rabbitMQ配置
     * @return
     */
    @Bean
    public RabbitMQConfig rabbitMQConfig(){
        return new RabbitMQConfig();
    }
}
