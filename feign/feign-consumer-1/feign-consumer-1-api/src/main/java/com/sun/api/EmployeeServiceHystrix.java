package com.sun.api;

import org.springframework.stereotype.Service;

/**
 * Description: <br/>
 * Date: 2018-08-18
 *
 * @author Sun
 */
@Service
public class EmployeeServiceHystrix implements EmployeeServiceApi {

    @Override
    public String version() {
        return "employee default version -1";
    }

    @Override
    public String call() {
        return "employee default call";
    }
}
