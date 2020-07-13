/**
 * @Project: wechat-common
 * @Package: com.chinahuik.commons.service
 * @Author: open-source@chinahuik.com
 * @Time: 2019年12月7日 下午10:50:00
 * @File: CacheService.java
 *
 */
package com.chinahuik.KubernetesManager.service;

/**
 * @author open-source@chinahuik.com
 *
 */
public interface CacheService {
	/**
	 *
	 * 缓存对象
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月7日 下午10:52:20
	 * @param key  键
	 * @param bean 对象
	 *
	 */
	void cacheBean(String key, Object bean);

	/**
	 *
	 * 缓存对象
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月7日 下午10:52:20
	 * @param key     键
	 * @param bean    对象
	 * @param timeout 过期时间，单位秒
	 *
	 */
	void cacheBean(String key, Object bean, long timeout);

	/**
	 *
	 * 缓存对象
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月7日 下午10:52:20
	 * @param key   键
	 * @param value 对象
	 *
	 */
	void cacheString(String key, String value);

	/**
	 *
	 * 缓存对象
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月7日 下午10:52:20
	 * @param key     键
	 * @param value   对象
	 * @param timeout 过期时间，单位秒
	 *
	 */
	void cacheString(String key, String value, long timeout);

	/**
	 *
	 * 获取缓存对象
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月7日 下午10:55:40
	 * @param key       键
	 * @param beanClass 对象类型
	 * @return
	 *
	 */
	<T> T getCache(String key, Class<T> beanClass);

	/**
	 *
	 * 获取过期时间，单位秒
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月7日 下午10:57:26
	 * @param key
	 * @return
	 *
	 */
	long getExpire(String key);

	/**
	 *
	 * 获取cache
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月11日 下午11:37:45
	 * @param key
	 * @return
	 *
	 */
	String getString(String key);

	/**
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年12月23日 上午12:50:49
	 * @param userid
	 *
	 */
	void removeBean(String userid);

	/**
	 * TODO
	 *
	 * @Author: open-source@chinahuik.com
	 * @Time: 2020年1月1日 上午12:20:14
	 * @param key
	 * @param timeout
	 *
	 */
	void setExpire(String key, int timeout);
}
