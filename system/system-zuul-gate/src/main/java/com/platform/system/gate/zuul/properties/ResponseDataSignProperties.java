package com.platform.system.gate.zuul.properties;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Maps;

/** 响应数据签名
 * 
 * */
@Data
@NoArgsConstructor
@ConfigurationProperties(ResponseDataSignProperties.PREFIX)
public class ResponseDataSignProperties {

    public static final String PREFIX = "zuul.security.response-data-sign";

    /** 是否启用 */
    private boolean enabled;
    
    /** 是否启用所有 */
    private boolean enableAll;
    
    /** 版本号字段 */
    private String versionField;
    
    /** 最低版本号 */
    private String minVersion;

    /** 签名字段 */
    private String signField;
    
    /** 签名KEY */
    private String signKey;
    
    /**
     * 参与签名的请求头
     */
    private List<String> joinSignHeaders;

    /** 路由策略 */
    private Map<String, Policy> policies = Maps.newHashMap();

    /** 根据路由id获取对应的策略
     * 
     * @param key
     * @return */
    public Optional<Policy> getPolicy(String key) {
        return Optional.ofNullable(policies.getOrDefault(key, null));
    }

    /** 策略
     * 
     * */
    @Data
    @NoArgsConstructor
    public static class Policy {

        /** 是否启用 */
        private boolean enabled;

        /** 签名字段 */
        private String signField;
        
        /** 签名KEY */
        private String signKey;
        
        /**
         * 参与签名的请求头
         */
        private List<String> joinSignHeaders;
    }
}
