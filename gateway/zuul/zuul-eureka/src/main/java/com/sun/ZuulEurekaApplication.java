package com.sun;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Description: <br/>
 * Date: 2018-07-30
 *
 * @author Sun
 */
@EnableZuulProxy
@SpringCloudApplication
public class ZuulEurekaApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ZuulEurekaApplication.class).web(true).run(args);
    }

}