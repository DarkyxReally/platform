package com.platform.api.gate.zuul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.platform.api.gate.configuration.properties.RequestIpStatsProperties;
import com.platform.system.common.util.DateUtils;

import java.util.Date;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * 请求IP统计
 * @version: 1.0
 */
@Configuration
@ConditionalOnProperty(prefix = RequestIpStatsProperties.PREFIX, name = "enabled", havingValue = "true")
public class RequestIpStatsRecorder {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	private static final String PREFIX = "REQUEST:IP";

	public void record(String path, String ipAddress, String idUser) {
		String key = getRedisKey(path, ipAddress);
		BoundSetOperations<String, String> boundSetOps = stringRedisTemplate.boundSetOps(key);
		boundSetOps.add(idUser);
		Long expire = boundSetOps.getExpire();
        if (expire == null || expire == -1) {
			boundSetOps.expire(7, TimeUnit.DAYS);
        }
	}

	private String getRedisKey(String path, String ipAddress) {
		StringJoiner joiner = new StringJoiner(":");
		joiner.add(PREFIX);
		joiner.add(DateUtils.format(new Date(), DateUtils.DateFormat.NONE));
		joiner.add(path);
		joiner.add(ipAddress);
		return joiner.toString();
	}

}
