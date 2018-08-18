package com.sun.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Description: <br/>
 * Date: 2018-07-31
 *
 * @author Sun
 */
@FeignClient("FEIGN-CONSUMER-2")
public interface CompanyServiceApi {

    @GetMapping("/com/version")
    String version();

    @GetMapping("/com/call")
    String call();
}
