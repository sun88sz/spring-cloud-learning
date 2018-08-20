package com.sun.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Description: <br/>
 * Date: 2018-07-31
 *
 * @author Sun
 */
@FeignClient(value = "FEIGN-CONSUMER-1", path = "emp", fallback = EmployeeServiceHystrix.class)
public interface EmployeeServiceApi {

    @GetMapping("/version")
    String version();

    @GetMapping("/call")
    String call();
}
