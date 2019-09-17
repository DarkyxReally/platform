package com.platform.system.gate.zuul.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * IP黑白名单限制配置
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(IpLimitProperties.PREFIX)
public class IpLimitProperties {

    public static final String PREFIX = "zuul.security.ip-limit";

    /** 是否启用 */
    private boolean enabled;

    /** 白名单 */
    private List<String> whiteIps = new ArrayList<>();

    /** 黑名单 */
    private List<String> blackIps = new ArrayList<>();

    /** 路由策略 */
    private Map<String, Policy> policies = Maps.newHashMap();

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

        /** 白名单 */
        private List<String> whiteIps = Lists.newArrayList();
        
        /** 黑名单 */
        private List<String> blackIps = Lists.newArrayList();
    }
}
