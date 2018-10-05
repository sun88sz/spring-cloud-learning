package com.sun;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Description: <br/>
 * Date: 2018-07-23
 *
 * @author Sun
 */
@EnableEurekaServer
@SpringBootApplication
@Slf4j
public class EurekaServer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServer.class).web(WebApplicationType.SERVLET).run(args);

        log.info("EurekaServer init completed");
    }
}
