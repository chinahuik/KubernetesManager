/**
 * 
 */
package com.chinahuik.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author open-source@chinahuik.com
 *
 */
public class HttpSessionUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpSessionUtil.class);
	/**
	 * 
	 */
	private HttpSession session = null;
	
	public HttpSessionUtil(HttpServletRequest request) {
		this.session = request.getSession();
	}
	public HttpSessionUtil(HttpSession session) {
		this.session = session;
	}
	public void setAttribute(String key,Object value){
		try {
			if (value instanceof String) {
				String vString = (String) value;
				session.setAttribute(key, vString);
			}else {
				String vString = JsonUtil.toJson(value);
				session.setAttribute(key, vString);
			}			
		} catch (Exception e) {
			logger.warn("set session attribute {} failed:{}",key,e);
		}
	}
	public String getAttribute(String key) {
		try {
			String value = (String)session.getAttribute(key);
			return value;
		} catch (Exception e) {
			logger.warn("get attribute {} failed:{}",key,e);
		}
		return null;
	}
	public <T> T getAttribute(String key,Class<T> clazz) {
		try {
			String value = (String)session.getAttribute(key);
			return JsonUtil.fromJson(value, clazz);
		} catch (Exception e) {
			logger.warn("get attribute {} failed:{}",key,e);
		}
		return null;
	}
	public void removeAttribute(String key) {
		try {
			session.removeAttribute(key);
		} catch (Exception e) {
			logger.warn("remove attribute {} failed:{}",key,e);
		}
	}

}
