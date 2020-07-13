/**
 * 
 */
package com.chinahuik.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author open-source@chinahuik.com
 *
 */
public class CookieUtil {
	private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);
    /**
     * 添加Cookie
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param domain
     * @param maxAge
     * @param isHttpOnly
     * @param isSecure
     */
	public static void addCookie(HttpServletResponse response,String cookieName,String cookieValue,String domain,int maxAge,boolean isHttpOnly,boolean isSecure) {
		//cookie 信息拼接
    	StringBuffer buffer = new StringBuffer();
    	//null or empty cookie value do nothing
    	if (cookieValue == null || cookieValue.trim().isEmpty()) {
			return;
		}
    	buffer.append(cookieName).append("=").append(cookieValue);
    	if (maxAge==0) {
			buffer.append(";Expires=Thu,01-Jan-1970 01:00:00 GMT");
		}else if (maxAge >0) {
			buffer.append(";Max-Age=").append(maxAge);			
		}
    	if (domain !=null && !domain.trim().isEmpty()) {
			buffer.append(";domain=").append(domain);
		}
    	buffer.append(";path=/");
    	if (isHttpOnly) {
			buffer.append(";HTTPOnly");
		}
    	if (isSecure) {
			buffer.append(";secure");
		}
    	response.addHeader("Set-Cookie", buffer.toString());
    	logger.info("Set-Cookie:{}",buffer.toString());		
	}
    /**
     * 清除所有cookie
     * @param request
     * @param response
     */
	public static void removeCookies(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return;
		}
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            Cookie killMyCookie = new Cookie(name, null);
            killMyCookie.setMaxAge(0);
            killMyCookie.setPath("/");
            response.addCookie(killMyCookie);
        }
	}
	/**
	 * 
	 * 获取cookie值
	 * @Author: open-source@chinahuik.com
	 * @Time: 2019年11月15日 上午12:06:19
	 * @param request
	 * @param name
	 * @return
	 *
	 */
	public static String getCookieValue(HttpServletRequest request,String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            String cName = cookie.getName();
	            if (cName.equals(name)) {
	            	return cookie.getValue();
	            }
	        }
		}
        return null;
		
	}
}
