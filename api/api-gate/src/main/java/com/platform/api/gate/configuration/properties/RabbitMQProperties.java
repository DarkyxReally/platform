package com.platform.api.gate.configuration.properties;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 防止重复请求配置
 * @version: 1.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = RabbitMQProperties.PREFIX)
public class RabbitMQProperties {

    public static final String PREFIX = "spring.rabbitmq";

    private String host;
    
    private int port;
    
    private String username;
    
    private String password;
    
    private String virtualHost = "/";
}
