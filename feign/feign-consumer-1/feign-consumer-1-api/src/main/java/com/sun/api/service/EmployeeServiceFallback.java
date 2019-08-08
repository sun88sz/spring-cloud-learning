package com.sun.api.service;

import com.sun.ResponseResult;
import com.sun.api.bean.Employee;
import org.springframework.stereotype.Service;

/**
 * Description: <br/>
 * Date: 2018-08-18
 *
 * @author Sun
 */
@Service
public class EmployeeServiceFallback implements EmployeeServiceApi {

    @Override
    public String version() {
        return "employee default version -1";
    }

    @Override
    public Employee call() {
        return new Employee(-1L,"error");
    }

    @Override
    public ResponseResult<Employee> call2() {
        return ResponseResult.ok(new Employee(-1L,"error"));
    }
}
