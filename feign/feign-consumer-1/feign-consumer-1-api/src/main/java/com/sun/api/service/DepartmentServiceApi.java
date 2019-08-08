package com.sun.api.service;

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
        contextId = "FEIGN-CONSUMER-1-DEPT",
        path = "dept")
public interface DepartmentServiceApi {

    @GetMapping("/version")
    String version();

    @GetMapping("/call")
    String call();
}
