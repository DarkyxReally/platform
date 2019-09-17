package com.platform.system.gate.zuul.properties;

import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import com.google.common.collect.Maps;

/** IP用户统计配置
 * 
 * */
@Data
@Validated
@NoArgsConstructor
@ConfigurationProperties(IpUserStatsProperties.PREFIX)
public class IpUserStatsProperties {

    public static final String PREFIX = "zuul.stats.ip-user";

    /** 是否启用 */
    private boolean enabled;
    
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
    }

}
