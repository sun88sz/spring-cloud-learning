package com.sun.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.sun.ResponseResult;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.Reader;

@Slf4j
@Configuration
public class CustomerErrorDecoder implements ErrorDecoder {

    public Exception decode(String methodKey, Response response) {
        ObjectMapper om = new ObjectMapper();
        Exception exception = null;
        try {
            Reader reader = response.body().asReader();
            String s = Util.toString(reader);
            ResponseResult resEntity = om.readValue(s, ResponseResult.class);
//为了说明我使用的 WebApplicationException 基类，去掉了封装
//            exception = new WebApplicationException(javax.ws.rs.core.Response.status(response.status()).entity(resEntity).type(MediaType.APPLICATION_JSON).build());
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        // 这里只封装4开头的请求异常
        if (400 <= response.status() && response.status() < 500) {
            exception = new HystrixBadRequestException("request exception wrapper", exception);
        } else {
            log.error(exception.getMessage(), exception);
        }
        return exception;
    }
}
