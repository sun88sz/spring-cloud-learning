package com.sun.controller;

import com.sun.api.EmployeeServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: <br/>
 * Date: 2018-07-31
 *
 * @author Sun
 */
@RequestMapping
@RestController
public class EmpConsumerController {

    @Autowired
    private EmployeeServiceApi employeeServiceApi;

    @GetMapping("/test")
    public String test() {
        return employeeServiceApi.get();
    }

}
