package com.platform.api.gate.zuul;

import java.util.Date;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.platform.api.gate.configuration.properties.RequestStatsProperties;
import com.platform.api.gate.configuration.properties.RequestStatsProperties.Policy.Type;
import com.platform.system.common.util.DateUtils;


/**
 * 请求统计
 * @version: 1.0
 */
@Configuration
@ConditionalOnProperty(prefix = RequestStatsProperties.PREFIX, name = "enabled", havingValue = "true")
public class RequestStatsRecorder {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	private static final String PREFIX = "REQUEST:STATS";

	public void recordUrl(String path) {
		String key = getRedisKey(path, Type.URL.toString());
		BoundValueOperations<String, String> boundValueOps = stringRedisTemplate.boundValueOps(key);
		boundValueOps.increment(1L);
		Long expire = boundValueOps.getExpire();
        if (expire == null || expire == -1) {
            boundValueOps.expire(7, TimeUnit.DAYS);
        }
	}

	public void recordIp(String path, String ipAddress) {
		String key = getRedisKey(path, Type.ORIGIN.toString());
		BoundHashOperations<String, String, String> boundHashOps = stringRedisTemplate.boundHashOps(key);
		boundHashOps.increment(ipAddress, 1L);
		Long expire = boundHashOps.getExpire();
        if (expire == null || expire == -1) {
            boundHashOps.expire(7, TimeUnit.DAYS);
        }
	}

	public void recordUser(String path, String idUser) {
		String key = getRedisKey(path, Type.USER.toString());
		BoundHashOperations<String, String, String> boundHashOps = stringRedisTemplate.boundHashOps(key);
		boundHashOps.increment(idUser, 1L);
		Long expire = boundHashOps.getExpire();
        if (expire == null || expire == -1) {
            boundHashOps.expire(7, TimeUnit.DAYS);
        }
	}

	private String getRedisKey(String path, String type) {
		StringJoiner joiner = new StringJoiner(":");
		joiner.add(PREFIX);
		joiner.add(DateUtils.format(new Date(), DateUtils.DateFormat.NONE));
		joiner.add(path);
		joiner.add(type);
		return joiner.toString();
	}

}
