package com.sun.controller;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: <br/>
 * Date: 2018-07-31
 *
 * @author Sun
 */
@RestController
@RequestMapping("/emp")
@Slf4j
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/version")
	public String version(HttpServletRequest request) {
		log.info("version");
		log.info("sessionId [{}]", request.getSession().getId());

		return "consumer-1";
	}

	@GetMapping("/call")
	public String call(HttpServletRequest request) {
		log.info("call");
		log.info("sessionId [{}]", request.getSession().getId());
		
//        在发送到trace-2之前sleuth会为在该请求的Header中增加实现跟踪需要的重要信息，主要有下面这几个（更多关于头信息的定义我们可以通过查看org.springframework.cloud.sleuth.Span的源码获取）：
//
//        X-B3-TraceId：一条请求链路（Trace）的唯一标识，必须值
//        X-B3-SpanId：一个工作单元（Span）的唯一标识，必须值
//        X-B3-ParentSpanId:：标识当前工作单元所属的上一个工作单元，Root Span（请求链路的第一个工作单元）的该值为空
//        X-B3-Sampled：是否被抽样输出的标志，1表示需要被输出，0表示不需要被输出
//        X-Span-Name：工作单元的名称

		String xB3TraceId = request.getHeader("X-B3-TraceId");
		String xB3SpanId = request.getHeader("X-B3-SpanId");
		String xB3ParentSpanId = request.getHeader("X-B3-ParentSpanId");
		String xB3Sampled = request.getHeader("X-B3-Sampled");
		String xSpanName = request.getHeader("X-Span-Name");
		log.info("==============================SLEUTH-APPLICATION-1 BY CALL ==============================");
		log.info("X-B3-TraceId [{}] （一条请求链路（Trace）的唯一标识）", xB3TraceId);
		log.info("X-B3-SpanId [{}] （一个工作单元（Span）的唯一标识）", xB3SpanId);
		log.info("X-B3-ParentSpanId [{}] （标识当前工作单元所属的上一个工作单元）", xB3ParentSpanId);
		log.info("X-B3-Sampled [{}] （是否被抽样输出的标志，1表示需要被输出，0表示不需要被输出）", xB3Sampled);
		log.info("X-Span-Name [{}] （工作单元的名称）", xSpanName);


		employeeService.call();


		return "emp-call";
	}
}
