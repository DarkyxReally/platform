package com.platform.system.gate.zuul.properties;

import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Maps;

/** 请求追踪
 * 
 * */
@Getter
@Setter
@ConfigurationProperties(TraceProperties.PREFIX)
public class TraceProperties {

    public static final String PREFIX = "zuul.trace";

    /** 是否启用 */
    private boolean enabled;
    
    /**
     * MDC中的traceKey
     */
    private String traceKey = "TRACE_ID";
    
    /**
     * IP地址
     */
    private String ipKey = "HOST_IP";
    
    /**
     * 是否在代理服务器之后
     */
    private boolean behindProxy;

    /**
     * 追踪信息配置
     */
    private Trace trace;
    
    /**
     * 追踪类型配置
     */
    @Data
    @NoArgsConstructor
    public static class Trace{
        /**
         * 请求id
         */
        private String id;
        /**
         * 请求ip
         */
        private String ip;
    }

    /** 路由策略
     * 
     * */
    @Data
    @NoArgsConstructor
    public static class Policy {

        /** 是否启用 */
        private boolean enabled;

        /**
         * 追踪信息配置
         */
        private Trace trace;
    }

    /** 路由策略 */
    private Map<String, Policy> policies = Maps.newHashMap();

    public Optional<Policy> getPolicy(String key) {
        return Optional.ofNullable(policies.getOrDefault(key, null));
    }
}
