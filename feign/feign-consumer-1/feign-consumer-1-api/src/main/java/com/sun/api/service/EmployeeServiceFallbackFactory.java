package com.sun.api.service;

import com.sun.BusinessException;
import com.sun.ResponseResult;
import com.sun.api.bean.Employee;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class EmployeeServiceFallbackFactory implements FallbackFactory<EmployeeServiceApi> {

    @Override
    public EmployeeServiceApi create(Throwable throwable) {
        System.out.println(throwable);
        System.out.println(throwable.getMessage());

        if (throwable instanceof BusinessException) {
            BusinessException exception = (BusinessException) throwable;
//            error = exception.getErrorCode();
        } else {
//            error = ErrorCodes.EC_990000;
        }

        ResponseResult response =  ResponseResult.error("");
//        response.setError(error);

        return new EmployeeServiceApi() {
            @Override
            public String version() {
                return "employee default version -1";
            }

            @Override
            public Employee call() {
                return new Employee(-1L, "error");
            }

            /**
             * 直接返回response
             * @return
             */
            @Override
            public ResponseResult<Employee> call2() {
                return response;
            }
        };
    }
}
