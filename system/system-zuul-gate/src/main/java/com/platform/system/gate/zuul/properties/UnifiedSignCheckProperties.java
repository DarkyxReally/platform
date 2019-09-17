package com.platform.system.gate.zuul.properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Maps;

/** 统一的接口签名校验配置
 * @version: 1.0 
 * */
@Data
@NoArgsConstructor
@ConfigurationProperties(UnifiedSignCheckProperties.PREFIX)
public class UnifiedSignCheckProperties {

    public static final String PREFIX = "zuul.security.unified-sign-check";

    /** 是否启用 */
    private boolean enabled;
    
    /** 是否启用所有 */
    private boolean enableAll;

    /** 接口key和密钥的键值对 */
    private Map<String, String> keyMap = new HashMap<String, String>();

    /** 接口KEY对应的IP限制 */
    private Map<String, List<String>> keyIpMap = new HashMap<String, List<String>>();

    /** 策略 */
    private Map<String, Policy> policies = Maps.newHashMap();

    /** 默认 超过60秒即可认为是过期请求 */
    private long expired = 1000 * 60L;

    public Optional<Policy> getPolicy(String key) {
        return Optional.ofNullable(policies.getOrDefault(key, null));
    }

    @Data
    @NoArgsConstructor
    public static class Policy {

        /** 是否启用 */
        private boolean enabled;

        /** 接口key和密钥的键值对 */
        private Map<String, String> keyMap = Maps.newHashMap();

        /** 接口KEY对应的IP限制 */
        private Map<String, List<String>> keyIpMap = Maps.newHashMap();
    }
}
