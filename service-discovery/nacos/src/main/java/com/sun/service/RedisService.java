package com.sun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${config.test1}")
    private String test1;
    
    public String xx(){

        System.out.println(test1);

        redisTemplate.opsForValue().set("a","b",60, TimeUnit.MINUTES);

        return redisTemplate.opsForValue().get("a");
    }
}
