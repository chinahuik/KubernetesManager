/**
 * @Project: wechat-common
 * @Package: com.chinahuik.commons.service
 * @Author: open-source@chinahuik.com
 * @Time: 2019年12月11日 下午11:23:30
 * @File: ConfigService.java
 *
 */
package com.chinahuik.KubernetesManager.service;

/**
 * @author open-source@chinahuik.com
 *
 */
public interface ConfigService {
	/**
	 * 
	 * 配置设置
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月11日 下午11:24:34
	 * @param key
	 * @param value
	 *
	 */
	void setConfig(String key,String value);
	/**
	 * 
	 * 配置获取
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月11日 下午11:24:40
	 * @param key
	 * @return
	 *
	 */
	String getString(String key);
	/**
	 * 
	 * 配置获取
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月11日 下午11:24:44
	 * @param key
	 * @return
	 *
	 */
	int getInt(String key);
}
