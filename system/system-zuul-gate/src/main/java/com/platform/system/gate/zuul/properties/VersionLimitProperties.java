package com.platform.system.gate.zuul.properties;

import java.util.Map;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Maps;

/** 版本号校验配置
 * 
 * @version: 1.0 
 * */
@Getter
@Setter
@ConfigurationProperties(VersionLimitProperties.PREFIX)
public class VersionLimitProperties {

    public static final String PREFIX = "zuul.security.version-limit";

    /** 是否启用 */
    private boolean enabled;
    
    /** 是否启用所有 */
    private boolean enableAll;

    /** 版本号字段 */
    private String versionField;

    /** 最低版本 */
    private String minVersion;

    /** 最高版本 */
    private String maxVersion;

    /** 路由策略 */
    @NotNull
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

        /** 版本号字段 */
        private String versionField;

        /** 最低版本 */
        private String minVersion;

        /** 最高版本 */
        private String maxVersion;
    }
}
