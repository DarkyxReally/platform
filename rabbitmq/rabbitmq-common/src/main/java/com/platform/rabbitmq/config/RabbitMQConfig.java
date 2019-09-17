package com.platform.rabbitmq.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * rabbitMq 配置
 * @version: 1.0
 */
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host:null}")
    private String host;
    @Value("${spring.rabbitmq.port:5672}")
    private int port;
    @Value("${spring.rabbitmq.username:null}")
    private String userName;
    @Value("${spring.rabbitmq.password:null}")
    private String password;
    @Value("${spring.rabbitmq.virtualHost:/}")
    private String virtualHost;
    
    public String getHost(){
        if ("null".equals(host)){
            return null;
        }
        return host;
    }
    
    public void setHost(String host){
        this.host = host;
    }
    
    public int getPort(){
        return port;
    }
    
    public void setPort(int port){
        this.port = port;
    }
    
    public String getUserName(){
        if ("null".equals(userName)){
            return null;
        }
        return userName;
    }
    
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    public String getPassword(){
        if ("null".equals(password)){
            return null;
        }
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getVirtualHost(){
        return virtualHost;
    }
    
    public void setVirtualHost(String virtualHost){
        this.virtualHost = virtualHost;
    }
}
