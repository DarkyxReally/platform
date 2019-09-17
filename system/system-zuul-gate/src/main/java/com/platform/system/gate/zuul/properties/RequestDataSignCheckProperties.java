package com.platform.system.gate.zuul.properties;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/** 请求数据签名校验
 * 
 * @version: 1.0
 * */
@Data
@NoArgsConstructor
@ConfigurationProperties(RequestDataSignCheckProperties.PREFIX)
public class RequestDataSignCheckProperties {

    public static final String PREFIX = "zuul.security.request-data-sign-check";

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
    
    /** 是否允许签名字段值为空   */
    private boolean allowEmptySignValue;
    
    /** 签名KEY */
    private String signKey;
    
    /** 参与签名的请求头字段 */
    private List<String> headers = Lists.newArrayList();

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
        
        /** 版本号字段 */
        private String versionField;

        /** 签名字段 */
        private String signField;
        
        /** 最低版本号 */
        private String minVersion;
        
        /** 是否允许签名字段值为空   */
        private boolean allowEmptySignValue;
        
        /** 签名KEY */
        private String signKey;
        
        /** 参与签名的请求头字段 */
        private List<String> headers = Lists.newArrayList();
    }
}
