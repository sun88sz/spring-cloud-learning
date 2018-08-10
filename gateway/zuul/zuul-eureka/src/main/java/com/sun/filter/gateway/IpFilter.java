package com.sun.filter.gateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ip过滤
 *
 * @author Sun
 * @date 2018-08-09
 */
@Slf4j
public class IpFilter extends ZuulFilter {

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}
	
	static List<String> blacklist;
	
	//配置本地IP黑名单，生产环境可放入数据库或者redis中
	static {
		blacklist = new ArrayList<>();
		blacklist.addAll(Arrays.asList( "192.168.0.1"));
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest req = ctx.getRequest();
		String ipAddr = this.getIpAddr(req);
		log.info("请求IP地址为：[{}]", ipAddr);
		log.info(req.getRequestURI());
		
		if (blacklist.contains(ipAddr)) {
			log.info("IP地址校验不通过！！！");
			ctx.setResponseStatusCode(401);
			ctx.setSendZuulResponse(false);
			ctx.setResponseBody("IpAddr is forbidden!");
		}
		log.info("IP校验通过");
		
		return null;
	}

	/**
	 * 获取Ip地址
	 *
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {

		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}