package com.sun.controller;

import com.sun.BusinessException;
import com.sun.ResponseResult;
import com.sun.api.bean.Employee;
import lombok.extern.slf4j.Slf4j;
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
@RestController
@RequestMapping("/emp")
@Slf4j
public class EmployeeController {

    @GetMapping("/version")
    public String version(HttpServletRequest request) {
        log.info("version");
        log.info("sessionId [{}]", request.getSession().getId());

        return "consumer-1";
    }

    @GetMapping("/call")
    public ResponseResult<Employee> call(HttpServletRequest request) {
        log.info("call");
        log.info("sessionId [{}]", request.getSession().getId());

        if (Math.random() > 0.5) {
            throw new BusinessException("errorrrrrrrrrrrrrrrrrrrrrrrrrrr");
        }

        return ResponseResult.ok(new Employee(1L, "test"));
    }

}
