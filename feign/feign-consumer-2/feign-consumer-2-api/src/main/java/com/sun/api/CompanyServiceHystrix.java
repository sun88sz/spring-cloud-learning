package com.sun.api;

import org.springframework.stereotype.Service;

/**
 * Description: CompanyServiceApi远程调用的断路器
 * Date: 2018-08-18
 *
 * @author Sun
 */
@Service
public class CompanyServiceHystrix implements CompanyServiceApi {

    @Override
    public String version() {
        return "company default version -1";
    }

    @Override
    public String call() {
        return "company default call";
    }
}
