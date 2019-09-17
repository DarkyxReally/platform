package com.platform.api.gate.configuration.properties;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 请求统计配置
 * @version: 1.0
 */
@Data
@Validated
@NoArgsConstructor
@ConfigurationProperties(RequestIpStatsProperties.PREFIX)
public class RequestIpStatsProperties {

	public static final String PREFIX = "zuul.request-ip-stats";

	@NotNull
	private List<String> policies = Lists.newArrayList();
	private boolean enabled;

	public boolean contains(String id) {
		return policies.contains(id);
	}

}
