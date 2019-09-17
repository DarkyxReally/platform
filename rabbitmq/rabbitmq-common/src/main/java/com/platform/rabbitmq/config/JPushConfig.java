package com.platform.rabbitmq.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;

/**
 * 极光推送配置
 * @version: 1.0
 */
@Getter
@Setter
public class JPushConfig {
    /**
     * AppKey
     */
    @Value("${jpush.appkey}")
    private String appKey;

    /**
     * Master Secret
     */
    @Value("${jpush.master.secret}")
    private String masterSecret;
    
    @Value("${xiaomipush.packagename}")
    private String xiaomiPackageName;
    
    @Value("${xiaomipush.master.secret}")
    private String xiaomiSecret;
    
    @Value("${meizupush.master.secret}")
    private String meizuSecret;
    
    @Value("${meizupush.appkey}")
    private Long meizuAppKey;
    
    /**
     * apnsProduction 字段来设定推送环境。True 表示推送生产环境，False 表示要推送开发环境； 如果不指定则为推送生产环境
     */
    @Value("${jpush.apns.production}")
    private boolean apnsProduction;
    
    
}
