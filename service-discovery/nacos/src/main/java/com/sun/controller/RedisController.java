package com.sun.controller;

import com.sun.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    RedisService redisService;

    @RequestMapping("/redis")
    public String xx() {
        return redisService.xx();
    }
}
