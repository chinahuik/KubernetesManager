/**
 * @Project: wechat-common
 * @Package: com.chinahuik.commons.service.impl
 * @Author: open-source@chinahuik.com
 * @Time: 2019年12月11日 下午11:25:44
 * @File: ConfigServiceImpl.java
 *
 */
package com.chinahuik.KubernetesManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinahuik.KubernetesManager.service.CacheService;
import com.chinahuik.KubernetesManager.service.ConfigService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author open-source@chinahuik.com
 *
 */
@Service
@Slf4j
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	private CacheService cacheService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinahuik.commons.service.ConfigService#getInt(java.lang.String)
	 */
	@Override
	public int getInt(String key) {
		final String value = getString(key);
		if (value == null || value.trim().isEmpty()) {
			return 0;
		}
		try {
			return Integer.parseInt(value);
		} catch (final Exception e) {
			log.warn("parse int from {} failed: {}", value, e);
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinahuik.commons.service.ConfigService#getString(java.lang.String)
	 */
	@Override
	public String getString(String key) {
		return cacheService.getString(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinahuik.commons.service.ConfigService#setConfig(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void setConfig(String key, String value) {
		cacheService.cacheString(key, value);
	}

}
