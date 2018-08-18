package com.sun.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Description: <br/>
 * Date: 2018-07-31
 *
 * @author Sun
 */
@FeignClient("SLEUTH-APPLICATION-1")
public interface EmployeeServiceApi {

    @GetMapping("/emp/version")
    String version();

    @GetMapping("/emp/call")
    String call();
}
