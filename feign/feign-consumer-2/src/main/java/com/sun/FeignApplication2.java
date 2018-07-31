package com.sun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Description: <br/>
 * Date: 2018-07-31
 *
 * @author Sun
 */
@EnableFeignClients
@SpringBootApplication
public class FeignApplication2 {
    public static void main(String[] args) {
        SpringApplication.run(FeignApplication2.class);
    }
}
