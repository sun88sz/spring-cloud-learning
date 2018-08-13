package com.sun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanName;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.stereotype.Service;

/**
 * Description: <br/>
 * Date: 2018-08-11
 * <p>
 * 可以做成一个注解
 *
 * @author Sun
 */
@Service
public class EmployeeService {

    @Autowired
    private Tracer tracer;

    @SpanName("xx")
    public String call() {
        // 创建一个 span
        final Span span = tracer.createSpan("employee_span");

//        span.tag(Span.SPAN_LOCAL_COMPONENT_TAG_NAME, "employee_service");
        span.logEvent(Span.CLIENT_SEND);
        // 这里时调用第三方 API 的代码
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 添加一个tag属性
        tracer.addTag("x","y");
        span.tag("XX","YY");

        // 最后，在调用之后的 finally 中（确保异常后也被调用到），插入代码。
        span.tag(Span.SPAN_PEER_SERVICE_TAG_NAME, "employee_service");


        span.logEvent(Span.CLIENT_RECV);
        tracer.close(span);

        return null;
    }
}
