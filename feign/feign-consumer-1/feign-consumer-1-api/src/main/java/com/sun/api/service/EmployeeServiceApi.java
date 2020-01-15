package com.sun.api.service;

import com.sun.ResponseResult;
import com.sun.api.bean.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Description: <br/>
 * Date: 2018-07-31
 *
 * @author Sun
 */
@FeignClient(
        value = "FEIGN-CONSUMER-1",
        contextId = "FEIGN-CONSUMER-1-EMP",
        path = "emp",
//        fallback = EmployeeServiceHystrix.class,
        fallbackFactory = EmployeeServiceFallbackFactory.class
)
public interface EmployeeServiceApi {

    @GetMapping("/version")
    String version();

    @GetMapping("/call")
    Employee call();

    @GetMapping("/call2")
    ResponseResult<Employee> call2();
}
