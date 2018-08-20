package com.sun.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Description: <br/>
 * Date: 2018-07-31
 *
 * @author Sun
 */
@FeignClient(value = "FEIGN-CONSUMER-2", path = "/com", fallback = CompanyServiceHystrix.class)
public interface CompanyServiceApi {

    @GetMapping("/version")
    String version();

    @GetMapping("/call")
    String call();
}
