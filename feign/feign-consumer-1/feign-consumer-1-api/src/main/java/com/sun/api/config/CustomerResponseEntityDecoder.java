package com.sun.api.config;

import com.sun.ResponseResult;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;

import java.io.IOException;
import java.lang.reflect.Type;

public class CustomerResponseEntityDecoder extends ResponseEntityDecoder {

    public CustomerResponseEntityDecoder(Decoder decoder) {
        super(decoder);
    }


    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        if (response.status() == 500 || response.status() == 404) {
            return ResponseResult.error("100001", "服务不可用");
        }
        return super.decode(response, type);
    }
}
