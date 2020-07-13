/**
 * @Project: wechat-common
 * @Package: com.chinahuik.commons.service.impl
 * @Author: open-source@chinahuik.com
 * @Time: 2019年12月7日 下午10:58:44
 * @File: CacheServiceImpl.java
 *
 */
package com.chinahuik.KubernetesManager.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.chinahuik.KubernetesManager.service.CacheService;
import com.chinahuik.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author open-source@chinahuik.com
 *
 */
@Service
@Slf4j
public class CacheServiceImpl implements CacheService {
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@Value("${cache.default.namespace:cache}")
	private String hashKey;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.chinahuik.commons.service.CacheService#cacheBean(java.lang.String,
	 * java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void cacheBean(String key, Object bean) {
		try {
			final HashOperations<String, String, String> operations = redisTemplate
					.opsForHash();
			final String beanStr = JsonUtil.toJson(bean);
			operations.put(key, hashKey, beanStr);
			log.info("cache key {} with value {} ok", key, beanStr);
		} catch (final Exception e) {
			log.warn("cache key {} failed: {}", key, e);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.chinahuik.commons.service.CacheService#cacheBean(java.lang.String,
	 * java.lang.Object, long, java.util.concurrent.TimeUnit)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void cacheBean(String key, Object bean, long timeout) {
		try {
			final HashOperations<String, String, String> operations = redisTemplate
					.opsForHash();
			final String beanStr = JsonUtil.toJson(bean);
			operations.put(key, hashKey, beanStr);
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
			log.info("cache key {} with value {} ok", key, beanStr);
		} catch (final Exception e) {
			log.warn("cache key {} failed: {}", key, e);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.chinahuik.commons.service.CacheService#cacheString(java.lang.String,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void cacheString(String key, String value) {
		try {
			final ValueOperations<String, String> operations = redisTemplate
					.opsForValue();
			operations.set(key, value);
			log.info("cache key {} with value {} ok", key, value);
		} catch (final Exception e) {
			log.warn("cache key {} failed: {}", key, e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.chinahuik.commons.service.CacheService#cacheString(java.lang.String,
	 * java.lang.String, long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void cacheString(String key, String value, long timeout) {
		try {
			final ValueOperations<String, String> operations = redisTemplate
					.opsForValue();
			operations.set(key, value, timeout, TimeUnit.SECONDS);
			log.info("cache key {} with value {} ok", key, value);
		} catch (final Exception e) {
			log.warn("cache key {} failed: {}", key, e);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.chinahuik.commons.service.CacheService#getCache(java.lang.String,
	 * java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCache(String key, Class<T> beanClass) {
		try {
			final HashOperations<String, String, String> operations = redisTemplate
					.opsForHash();
			final String value = operations.get(key, hashKey);
			log.info("get cache {} value: {}", key, value);
			if (value == null) {
				return null;
			}
			final T bean = JsonUtil.fromJson(value, beanClass);
			return bean;
		} catch (final Exception e) {
			log.warn("get cache {} failed: {}", key, e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.chinahuik.commons.service.CacheService#getExpireSeconds(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public long getExpire(String key) {
		try {
			final long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
			return expire;
		} catch (final Exception e) {
			log.warn("get cache {} expire failed: {}", key, e);
		}
		return -1;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.chinahuik.commons.service.CacheService#getString(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getString(String key) {
		try {
			final ValueOperations<String, String> operations = redisTemplate
					.opsForValue();
			final String value = operations.get(key);
			log.info("get cache key {} with value {} ok", key, value);
			return value;
		} catch (final Exception e) {
			log.warn("get cache key {} failed: {}", key, e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.chinahuik.commons.service.CacheService#removeBean(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void removeBean(String key) {
		try {
			final HashOperations<String, String, String> operations = redisTemplate
					.opsForHash();
			operations.delete(key, hashKey);
			log.info("delete key {}  ok", key);
		} catch (final Exception e) {
			log.warn("cache key {} failed: {}", key, e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.chinahuik.commons.service.CacheService#setExpire(java.lang.String,
	 * int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setExpire(String key, int timeout) {
		try {
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
			log.info("cache key {} with timeout {} seconds ok", key, timeout);
		} catch (final Exception e) {
			log.warn("cache key {} failed: {}", key, e);
		}
	}

}
