/**
 *
 */
package com.chinahuik.KubernetesManager.aspect;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

/**
 * @author open-source@chinahuik.com
 *
 */
@Aspect
@Order(5)
@Component
public class WebLogAspect {
	ThreadLocal<Long> startTime = new ThreadLocal<>();
	private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		final HttpServletRequest request = attributes.getRequest();
		final Map<String, Object> details = getRequestDetails(request, null);
		logger.info("request: {}", JSONObject.toJSONString(details));
		// 处理完请求，返回内容
		logger.info("RESPONSE : " + JSONObject.toJSONString(ret));
		logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		startTime.set(System.currentTimeMillis());
		final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		final HttpServletRequest request = attributes.getRequest();
		final Map<String, Object> details = getRequestDetails(request, joinPoint);
		logger.info("request: {}", JSONObject.toJSONString(details));
	}

	@Pointcut("execution(public * com.chinahuik.dal.web..*.*(..))")
	public void webLog() {

	}

	/**
	 * 获取头信息
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, String> getHeadersInfo(HttpServletRequest request) {
		final Map<String, String> map = new HashMap<>();
		final Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			final String key = (String) headerNames.nextElement();
			final String value = request.getHeader(key);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 获取请求详情信息
	 * 
	 * @param request   请求
	 * @param joinPoint
	 * @return
	 */
	private Map<String, Object> getRequestDetails(HttpServletRequest request,
			JoinPoint joinPoint) {
		// 基本信息
		final Map<String, Object> map = new HashMap<>();
		map.put("requestURL", request.getRequestURL().toString());
		map.put("requestURI", request.getRequestURI());
		map.put("queryString", request.getQueryString());
		map.put("remoteAddr", request.getRemoteAddr());
		map.put("remoteHost", request.getRemoteHost());
		map.put("remotePort", request.getRemotePort());
		map.put("localAddr", request.getLocalAddr());
		map.put("localName", request.getLocalName());
		map.put("method", request.getMethod());
		map.put("headers", getHeadersInfo(request));
		map.put("parameters", request.getParameterMap());
		if (joinPoint != null) {
			map.put("classMethod", joinPoint.getSignature().getDeclaringTypeName() + "."
					+ joinPoint.getSignature().getName());
			map.put("args", JSONObject.toJSONString(joinPoint.getArgs()));
		}
		return map;
	}
}
