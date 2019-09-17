
package com.platform.system.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import lombok.extern.slf4j.Slf4j;

/**
 * @version: 1.0
 */
@Slf4j
public class RedisService {

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	/**
	 * 赋值，带超时时间
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public Long set(String key, String value, long seconds) {
		return redisTemplate.execute((RedisCallback<Long>) c -> {
			c.set(key.getBytes(), value.getBytes());
			c.expire(key.getBytes(), seconds);
			return 1L;
		});
	}

	/**
	 * 赋值，带指定超时时间
	 * 
	 * @param key
	 * @param value
	 * @param atDateTime
	 *            指定时间失败
	 * @return
	 */
	public Long set(String key, String value, Date atDateTime) {
		return redisTemplate.execute((RedisCallback<Long>) c -> {
			long time = atDateTime.getTime();
			c.set(key.getBytes(), value.getBytes());
			c.expireAt(key.getBytes(), time);
			return 1L;
		});
	}

	/**
	 * set赋值，
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public Long sset(String key, String value) {
		return redisTemplate
				.execute((RedisCallback<Long>) c -> {
					SetOperations<Object, Object> opsForSet = redisTemplate
							.opsForSet();
					opsForSet.add(key, value);
					return 1L;
				});
	}

	/**
	 * set查询是否存在,订制使用，
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public boolean sHasMember(String key, String value) {
		SetOperations<Object, Object> opsForSet = redisTemplate.opsForSet();
		return opsForSet.isMember(key, value);
	}

	/**
	 * 赋值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long set(String key, String value) {
		return redisTemplate.execute((RedisCallback<Long>) c -> {
			c.set(key.getBytes(), value.getBytes());
			return 1L;
		});
	}

	/**
	 * 判断key是否存在
	 * 
	 * @param key
	 * @return
	 */
	public Boolean exist(String key) {
		return redisTemplate.execute((RedisCallback<Boolean>) c -> c.exists(key
				.getBytes()));
	}

	/**
	 * 取值
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		if (!exist(key)) {
			return null;
		}
		return redisTemplate.execute((RedisCallback<String>) c -> {
			try {
				return new String(c.get(key.getBytes()), "utf-8");
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage(), e);
				return null;
			}
		});
	}

	/**
	 * 删除
	 * 
	 * @param key
	 * @return
	 */
	public Long del(String key) {
		return redisTemplate.execute((RedisCallback<Long>) c -> {
			return c.del(key.getBytes());
		});
	}

	/**
	 * 赋值
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Boolean hSet(String key, String field, String value) {
		return redisTemplate.execute((RedisCallback<Boolean>) c -> {
			return c.hSet(key.getBytes(), field.getBytes(), value.getBytes());
		});
	}

	public Boolean hSet(String key, String field, String value, long seconds) {
		return redisTemplate.execute((RedisCallback<Boolean>) c -> {
			Boolean flag = c.hSet(key.getBytes(), field.getBytes(),
					value.getBytes());
			c.expire(key.getBytes(), seconds);
			return flag;
		});
	}

	/**
	 * 取值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public String hGet(String key, String field) {
		if (!hExist(key, field)) {
			return null;
		}
		return redisTemplate.execute((RedisCallback<String>) c -> {
			try {
				return new String(c.hGet(key.getBytes(), field.getBytes()),
						"utf-8");
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage(), e);
				return null;
			}
		});
	}

	/**
	 * 判断字段是否存在
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public Boolean hExist(String key, String field) {
		return redisTemplate.execute((RedisCallback<Boolean>) c -> c.hExists(
				key.getBytes(), field.getBytes()));
	}

	/**
	 * 删除
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public Long hDel(String key, String field) {
		return redisTemplate.execute((RedisCallback<Long>) c -> {
			return c.hDel(key.getBytes(), field.getBytes());
		});
	}

	/**
	 * 无序集合添加
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public Long sAdd(String key, String value) {
		return redisTemplate.execute((RedisCallback<Long>) c -> {
			return c.sAdd(key.getBytes(), value.getBytes());
		});
	}

	/**
	 * 判断无序集合是否又该值
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public boolean sIsMember(String key, String value) {
		return redisTemplate.execute((RedisCallback<Boolean>) c -> {
			return c.sIsMember(key.getBytes(), value.getBytes());
		});
	}
}
