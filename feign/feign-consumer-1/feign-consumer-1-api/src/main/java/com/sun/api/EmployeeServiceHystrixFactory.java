package com.sun.api;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class EmployeeServiceHystrixFactory implements FallbackFactory<EmployeeServiceApi> {
    @Override
    public EmployeeServiceApi create(Throwable cause) {
        return new EmployeeServiceApi() {

            @Override
            public String version() {
                return "employee default version -1";
            }

            @Override
            public String call() {
                return "employee default call";
            }
        };
    }
}