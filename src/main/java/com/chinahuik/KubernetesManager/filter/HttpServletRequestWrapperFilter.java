/**
 *
 */
package com.chinahuik.KubernetesManager.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.chinahuik.KubernetesManager.wrapper.RepeatableHttpServletRequestWrapper;

/**
 * @author open-source@chinahuik.com
 *
 */
public class HttpServletRequestWrapperFilter implements Filter {
	@Override
	public void destroy() {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		ServletRequest requestWrapper = null;
		if (request instanceof HttpServletRequest) {
			requestWrapper = new RepeatableHttpServletRequestWrapper(
					(HttpServletRequest) request);
		}
		if (null == requestWrapper) {
			chain.doFilter(request, response);
		} else {
			chain.doFilter(requestWrapper, response);
		}

	}

	@Override
	public void init(FilterConfig filterConfig) {

	}

}
