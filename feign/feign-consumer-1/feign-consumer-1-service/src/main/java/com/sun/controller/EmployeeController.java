package com.sun.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: <br/>
 * Date: 2018-07-31
 *
 * @author Sun
 */
@RestController
@RequestMapping("/emp")
public class EmployeeController {

    @GetMapping("/no")
    public  String get(){
        return  "1";
    }

}
