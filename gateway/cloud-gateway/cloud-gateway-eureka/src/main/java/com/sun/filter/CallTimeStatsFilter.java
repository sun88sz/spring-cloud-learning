package com.sun.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 接口调用用时统计
 *
 * @author
 */
@Slf4j
public class CallTimeStatsFilter implements GlobalFilter, Ordered {

    private static final String COUNT_START_TIME = "countStartTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(COUNT_START_TIME, System.currentTimeMillis());
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    Long startTime = exchange.getAttribute(COUNT_START_TIME);
                    if (startTime != null) {
                        Long betweenTime = (System.currentTimeMillis() - startTime);
                        log.info("CALL_STATS_FILTER - [ {} ] ; [ {}ms ]", exchange.getRequest().getURI().getRawPath(), betweenTime);
                    }
                })
        );
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
