package com.platform.system.gate.zuul.biz;

import java.util.Date;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.platform.system.common.util.DateUtils;


/**
 * IP用户统计
 * @version: 1.0
 */
public class IpUserStatsRecorder {

	private final StringRedisTemplate stringRedisTemplate;
	
	public IpUserStatsRecorder(StringRedisTemplate stringRedisTemplate) {
        super();
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private static final String PREFIX = "REQUEST:IP_USER";

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
