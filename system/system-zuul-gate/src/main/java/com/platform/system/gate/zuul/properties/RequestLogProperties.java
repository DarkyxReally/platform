package com.platform.system.gate.zuul.properties;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Maps;

/** 请求日志记录配置
 * 
 * @version: 1.0 
 * */
@Getter
@Setter
@ConfigurationProperties(RequestLogProperties.PREFIX)
public class RequestLogProperties {

    public static final String PREFIX = "zuul.request-log";

    /** 是否启用 */
    private boolean enabled;
    /** 日志级别 */
    private Level logLevel;
    /** 是否打印请求参数 */
    private boolean logParams;
    /** 是否打印请求body */
    private boolean logReqeustBody;
    /** 是否打印请求头 */
    private boolean logHeaders;
    /** 忽略请求头 */
    private List<String> ingoreHeaders;

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
        /** 日志级别 */
        private Level logLevel;
        /** 是否打印请求参数 */
        private boolean logParams;
        /** 是否打印请求body */
        private boolean logReqeustBody;
        /** 是否打印请求头 */
        private boolean logHeaders;
        /** 忽略请求头 */
        private List<String> ingoreHeaders;
    }

    /** 日志级别
     * 
     * */
    public enum Level {
        DEBUG, INFO, WARN, ERROR
    }
}
