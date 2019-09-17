package com.platform.system.gate.zuul.properties;

import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import com.google.common.collect.Maps;

/** 响应数据加密
 * 
 * @version: 1.0 
 * */
@Data
@Validated
@NoArgsConstructor
@ConfigurationProperties(ResponseDataEncryptProperties.PREFIX)
public class ResponseDataEncryptProperties {

    public static final String PREFIX = "zuul.security.response-data-encrypt";

    /** 是否启用 */
    private boolean enabled;
    
    /** 是否启用所有 */
    private boolean enableAll;
    
    /** 版本号字段 */
    private String versionField;
    
    /** 最低版本号 */
    private String minVersion;

    /** 私钥 */
    private String priKey;
    
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

        /** 私钥 */
        private String priKey;
    }
}
