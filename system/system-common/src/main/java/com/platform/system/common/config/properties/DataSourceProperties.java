package com.platform.system.common.config.properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@ConfigurationProperties(prefix = DataSourceProperties.DATA_SOURCE, ignoreUnknownFields = true)
public class DataSourceProperties {

    // 对应配置文件里的配置键
    public final static String DATA_SOURCE = "spring.datasource";

    private String name;
    
    private String url;
    
    private String username;
    
    private String password;
    
    // ///////////////////druid参数///////////////////////////////////////////////////
    private String driverClassName;
    
    private String filters;
    
    private String maxActive;
    
    private String initialSize;
    
    private String maxWait;
    
    private String minIdle;
    
    private String timeBetweenEvictionRunsMillis;
    
    private String minEvictableIdleTimeMillis;
    
    private String validationQuery;
    
    private String testWhileIdle;
    
    private String testOnBorrow;
    
    private String testOnReturn;
    
    private String poolPreparedStatements;
    
    private String maxOpenPreparedStatements;
    
    /** 慢sql的计算时间 */
    private long slowSqlTimeMillis = 1000;
}