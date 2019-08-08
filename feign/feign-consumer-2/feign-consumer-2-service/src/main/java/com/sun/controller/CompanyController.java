package com.sun.controller;

import com.sun.ResponseResult;
import com.sun.api.bean.Employee;
import com.sun.api.service.DepartmentServiceApi;
import com.sun.api.service.EmployeeServiceApi;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/com")
@RestController
@Slf4j
public class CompanyController {

    @Autowired
    private EmployeeServiceApi employeeServiceApi;
    @Autowired
    private DepartmentServiceApi departmentServiceApi;

    @GetMapping("/version")
    public String test(HttpServletRequest request) {
        log.info("version");
        log.info("sessionId [{}]", request.getSession().getId());

        return "consumer-2";
    }

    @GetMapping("/call")
    public ResponseResult<String> get(HttpServletRequest request) {
        log.info("com call");
        log.info("sessionId [{}]", request.getSession().getId());
        Employee emp = employeeServiceApi.call();
        String dept = departmentServiceApi.call();
        return ResponseResult.ok("com-call - " + emp.getName() + " - " + dept);
    }


    @GetMapping("/call2")
    public ResponseResult<String> get2(HttpServletRequest request) {
        log.info("com call2");
        log.info("sessionId [{}]", request.getSession().getId());
        ResponseResult<Employee> call = employeeServiceApi.call2();
        Employee emp = call.getResult();
        return ResponseResult.ok("com-call - " + emp.getName() );
    }

}
