/**
 *
 */
package com.chinahuik.KubernetesManager.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chinahuik.KubernetesManager.filter.HttpServletRequestWrapperFilter;

/**
 * @author open-source@chinahuik.com
 *
 */
@Configuration
public class FilterConfig {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean filterRegister() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new HttpServletRequestWrapperFilter());
		registrationBean.addUrlPatterns("/**");
		registrationBean.setName("HttpServletRequestWrapperFilter");
		registrationBean.setOrder(1);
		return registrationBean;
	}
}
