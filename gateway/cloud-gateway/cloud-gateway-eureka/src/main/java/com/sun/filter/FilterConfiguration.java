package com.sun.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Sun
 * @date : 2018/9/27 17:17
 */
@Configuration
public class FilterConfiguration {

    @Bean
    public CallTimeStatsFilter statsFilter() {
        return new CallTimeStatsFilter();
    }

    @Bean
    public AuthSignatureFilter authSignatureFilter() {
        return new AuthSignatureFilter();
    }
}
