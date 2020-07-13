/**
 * @Project: wechat-config
 * @Package: com.chinahuik.config.config
 * @Author: open-source@chinahuik.com
 * @Time: 2019年12月11日 下午11:44:01
 * @File: ConfigInitizer.java
 *
 */
package com.chinahuik.KubernetesManager.config;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinahuik.KubernetesManager.model.SystemConfigModel;
import com.chinahuik.KubernetesManager.service.ConfigService;
import com.chinahuik.KubernetesManager.service.SystemConfigService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author open-source@chinahuik.com
 *
 */
@Component
@Slf4j
public class ConfigInitizer implements InitializingBean {

	@Autowired
	private SystemConfigService sysConfigService;
	@Autowired
	private ConfigService configService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("====开始初始化系统配置====");
		final List<SystemConfigModel> models = sysConfigService.list();
		for (final SystemConfigModel model : models) {
			configService.setConfig(model.getConfigName(), model.getConfigValue());
		}
	}

}
