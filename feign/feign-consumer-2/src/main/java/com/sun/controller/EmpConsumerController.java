package com.sun.controller;

import com.sun.api.EmployeeServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/version")
    public String test(HttpServletRequest request) {
        System.out.println("version");
        System.out.println(request.getSession().getId());

        return "consumer-2";
    }

    @GetMapping("/call")
    public String get(HttpServletRequest request) {
        System.out.println("call");
        System.out.println(request.getSession().getId());

        return "call - " + employeeServiceApi.version();
    }

}
