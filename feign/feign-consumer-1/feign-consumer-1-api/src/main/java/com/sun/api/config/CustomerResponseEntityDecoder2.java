package com.sun.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.BusinessException;
import com.sun.ResponseResult;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class CustomerResponseEntityDecoder2 implements Decoder {
    ObjectMapper mapper = new ObjectMapper();

    private Decoder decoder;

    public CustomerResponseEntityDecoder2(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Object decode(final Response response, Type type)
            throws IOException, FeignException {

        if (isParameterizeHttpEntity(type)) {
            type = ((ParameterizedType) type).getActualTypeArguments()[0];
            Object decodedObject = this.decoder.decode(response, type);
            return createResponse(decodedObject, response);
        } else if (isHttpEntity(type)) {
            return createResponse(null, response);
        } else {
            String s = Util.toString(response.body().asReader());
            ResponseResult responseResult = mapper.readValue(s, ResponseResult.class);
            if (responseResult.getStatus() == 1) {
                return responseResult.getResult();
            } else {
                return this.decoder.decode(response, type);
            }
        }
    }

    private boolean isParameterizeHttpEntity(Type type) {
        if (type instanceof ParameterizedType) {
            return isHttpEntity(((ParameterizedType) type).getRawType());
        }
        return false;
    }

    private boolean isHttpEntity(Type type) {
        if (type instanceof Class) {
            Class c = (Class) type;
            return HttpEntity.class.isAssignableFrom(c);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private <T> ResponseEntity<T> createResponse(Object instance, Response response) {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        for (String key : response.headers().keySet()) {
            headers.put(key, new LinkedList<>(response.headers().get(key)));
        }

        return new ResponseEntity<>((T) instance, headers,
                HttpStatus.valueOf(response.status()));
    }

}
