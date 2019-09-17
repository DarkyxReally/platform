package com.platform.api.gate.configuration.properties;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * IP黑白名单限流配置
 * @version: 1.0
 */
@Data
@Validated
@NoArgsConstructor
@ConfigurationProperties(IpLimitProperties.PREFIX)
public class IpLimitProperties {

	public static final String PREFIX = "zuul.iplimit";

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
		 * 白名单
		 */
		@NotNull
		private List<String> whiteIps = new ArrayList<>();
		/**
		 * 黑名单
		 */
		@NotNull
		private List<String> blackIps = new ArrayList<>();

	}
}
