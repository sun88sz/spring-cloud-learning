package com.sun.controller;

import javax.servlet.http.HttpServletRequest;

import com.sun.remote.EmployeeServiceApi;

import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/com")
@RestController
@Slf4j
public class CompanyController {

    @Autowired
    private EmployeeServiceApi employeeServiceApi;

    @GetMapping("/version")
    public String test(HttpServletRequest request) {
        log.info("version");
        log.info("sessionId [{}]", request.getSession().getId());

        return "consumer-2";
    }

    @GetMapping("/call")
    public String get(HttpServletRequest request) {
        log.info("com call");
        log.info("sessionId [{}]", request.getSession().getId());

        return "com-call ... " + employeeServiceApi.call();
    }

}
