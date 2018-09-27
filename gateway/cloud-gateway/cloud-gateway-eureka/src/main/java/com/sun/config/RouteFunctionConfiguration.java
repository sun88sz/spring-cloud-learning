package com.sun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author : Sun
 * @date : 2018/9/27 15:53
 * <p>
 * <p>
 * 路由断言工厂
 * 路由断言工厂有多种类型，根据请求的时间、host、路径、方法等等。如下定义的是一个基于路径的路由断言匹配。
 */
@Configuration
public class RouteFunctionConfiguration {

    /**
     * 会覆盖配置文件中配置的路由
     *
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> testFunRouterFunction() {
        RouterFunction<ServerResponse> route = RouterFunctions.route(
                RequestPredicates.path("/163/**"),
                request -> ServerResponse.ok().body(BodyInserters.fromObject("hello 163")));
        return route;
    }

}
