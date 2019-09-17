package com.platform.api.gate.configuration.properties;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 请求统计配置
 * @version: 1.0
 */
@Data
@Validated
@NoArgsConstructor
@ConfigurationProperties(RequestStatsProperties.PREFIX)
public class RequestStatsProperties {

	public static final String PREFIX = "zuul.request-stats";

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
		 * 统计类型
		 */
		@NotNull
		private List<Type> type = Lists.newArrayList();

		public enum Type {
			ORIGIN, USER, URL
		}

	}
}
