package com.sun.api;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * error方式获取 2
 */
@Configuration
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            // 这里直接拿到我们想过的异常信息
            String message = Util.toString(response.body().asReader());
            return new RuntimeException(message);
        } catch (IOException ignored) {
        }
        return decode(methodKey, response);
    }
}