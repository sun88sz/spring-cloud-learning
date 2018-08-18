package com.sun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Description: <br/>
 * Date: 2018-08-07
 *
 * @author Sun
 */
@EnableFeignClients
@SpringBootApplication
public class SleuthApplication2 {
    public static void main(String[] args) {
        SpringApplication.run(SleuthApplication2.class);
    }
}
