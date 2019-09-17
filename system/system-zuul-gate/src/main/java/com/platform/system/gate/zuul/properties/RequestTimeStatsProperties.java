package com.platform.system.gate.zuul.properties;

import java.util.Map;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import com.google.common.collect.Maps;

/**
 * 请求时间统计配置
 * @version: 1.0
 */
@Data
@Validated
@NoArgsConstructor
@ConfigurationProperties(RequestTimeStatsProperties.PREFIX)
public class RequestTimeStatsProperties {

	public static final String PREFIX = "zuul.stats.request-time";

	@NotNull
	private Map<String, Policy> policies = Maps.newHashMap();
	
	/**
	 * 是否启用(全局开关)
	 */
	private boolean enabled;
	
	/**
	 * 是否统计所有
	 */
	private boolean all;
	
	/**
	 * 慢请求(单位:毫秒)
	 * 默认超过2秒即算慢请求
	 */
	private int slowRequestMillisecond = 2000;

	public Optional<Policy> getPolicy(String key) {
		return Optional.ofNullable(policies.getOrDefault(key, null));
	}

	/**
	 * 策略
	 * @version: 1.0
	 */
	@Data
	@NoArgsConstructor
	public static class Policy {
	    
	    /**
	     * 是否启用
	     */
	    private boolean enabled;
		
	    /**
	     * 慢请求(单位:毫秒)
	     * 默认超过2秒即算慢请求
	     */
	    private int slowRequestMillisecond = 2000;
	}
}
