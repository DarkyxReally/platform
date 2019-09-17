package com.platform.system.gate.zuul.properties;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/** 请求统计配置
 * 
 * @version: 1.0 
 * */
@Data
@Validated
@NoArgsConstructor
@ConfigurationProperties(RequestStatsProperties.PREFIX)
public class RequestStatsProperties {

    public static final String PREFIX = "zuul.stats.request-count";

    /** 是否启用 */
    private boolean enabled;

    private Map<String, Policy> policies = Maps.newHashMap();

    public Optional<Policy> getPolicy(String key) {
        return Optional.ofNullable(policies.getOrDefault(key, null));
    }

    @Data
    @NoArgsConstructor
    public static class Policy {

        /** 是否启用 */
        private boolean enabled;

        /** 统计类型 */
        @NotNull
        private List<Type> type = Lists.newArrayList();

        public enum Type {
            ORIGIN, USER, URL
        }
    }
}
