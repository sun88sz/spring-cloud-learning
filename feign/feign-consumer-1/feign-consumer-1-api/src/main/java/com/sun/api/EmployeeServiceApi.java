package com.sun.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Description: <br/>
 * Date: 2018-07-31
 *
 * @author Sun
 */
@FeignClient("FEIGN-CONSUMER-1")
public interface EmployeeServiceApi {

    @GetMapping("/emp/version")
    String version();

    @GetMapping("/emp/call")
    String call();
}
