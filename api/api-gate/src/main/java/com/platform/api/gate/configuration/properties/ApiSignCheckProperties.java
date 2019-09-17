package com.platform.api.gate.configuration.properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import com.google.common.collect.Maps;

/**
 * 接口签名校验配置
 * @version: 1.0
 */
@Data
@Validated
@NoArgsConstructor
@ConfigurationProperties(ApiSignCheckProperties.PREFIX)
public class ApiSignCheckProperties {

	public static final String PREFIX = "zuul.sign-check";

	@NotNull
	private Map<String, Policy> policies = Maps.newHashMap();
	private boolean enabled;

	public Optional<Policy> getPolicy(String key) {
		return Optional.ofNullable(policies.getOrDefault(key, null));
	}

	@Data
	@NoArgsConstructor
	public static class Policy {
		
		/**
		 * 接口key和密钥的键值对
		 */
	    @NotNull
		private Map<String, String> keyMap = new HashMap<String, String>();
		
		/**
		 * 接口KEY对应的IP限制
		 */
		@NotNull
		private Map<String, List<String>> keyIpMap = new HashMap<String, List<String>>();
	}
}
