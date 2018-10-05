package com.sun.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author : Sun
 * @date : 2018/9/27 16:01
 * <p>
 * <p>
 * 过滤器工厂
 * 网关经常需要对路由请求进行过滤，进行一些操作，如鉴权之后构造头部之类的，过滤的种类很多，如增加请求头、增加请求 参数 、增加响应头和断路器等等功能。
 */
@Configuration
public class RouteLocatorConfiguration {


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                //basic proxy
                .route(r -> r.path("/baidu").uri("http://baidu.com:80/"))
                .route(r -> r.host("**.changeuri.org").and().header("X-Next-Url").uri("http://blueskykong.com"))
                .route(r -> r.host("**.changeuri.org").and().query("url").uri("http://blueskykong.com"))

                .build();
    }

    @Bean
    public RouteLocator customRouteLocator2(RouteLocatorBuilder builder) {
        //@formatter:off
        return builder.routes()
                .route(r -> r.path("/image/webp")
                        .filters(f -> f.stripPrefix(2).prefixPath("/xxxx").addResponseHeader("X-AnotherHeader", "baz")
                                .filter(((exchange, chain) -> Mono.empty()))
                        )
                        .uri("http://httpbin.org:80")
                        .order(0)
                        .id("xxxxxx")
                )
                .build();
        //@formatter:on
    }

}
