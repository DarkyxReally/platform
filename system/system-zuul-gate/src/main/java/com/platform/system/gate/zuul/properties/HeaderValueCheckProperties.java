package com.platform.system.gate.zuul.properties;

import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Maps;

/** 请求头值校验配置
 * 
 * @version: 1.0 
 * */
@Getter
@Setter
@ConfigurationProperties(HeaderValueCheckProperties.PREFIX)
public class HeaderValueCheckProperties {

    public static final String PREFIX = "zuul.security.header-value-check";

    /** 是否启用 */
    private boolean enabled;

    /** 请求头值映射 */
    private Map<String, String> headerValues = Maps.newLinkedHashMap();

    /** 路由策略 */
    private Map<String, Policy> policies = Maps.newHashMap();
    
    public Optional<Policy> getPolicy(String key) {
        return Optional.ofNullable(policies.getOrDefault(key, null));
    }

    /** 路由策略
     * 
     * */
    @Data
    @NoArgsConstructor
    public static class Policy {

        /** 是否启用 */
        private boolean enabled;

        /** 请求头值映射 */
        private Map<String, String> headerValues = Maps.newLinkedHashMap();
    }
}
