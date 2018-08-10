package com.sun.filter;

import com.sun.filter.gateway.AuthFilter;
import com.sun.filter.gateway.IpFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Sun
 * @date : 2018/8/7 14:52
 */
@Configuration
public class FilterConfiguration {

	@Bean
	public AuthFilter accessFilter() {
		return new AuthFilter();
	}

	@Bean
	public IpFilter ipFilter() {
		return new IpFilter();
	}
}
