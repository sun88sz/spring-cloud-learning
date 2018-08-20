
spring:
  redis:
    host: 127.0.0.1
    port: 6379
  cloud:
    gateway:
      routes:
      - id: fsh-house
        uri: lb://fsh-house
        predicates:
        - Path=/house/**
        filters:
        - name: RequestRateLimiter
          args:
            redis-rate-limiter.replenishRate: 10
            redis-rate-limiter.burstCapacity: 20
            key-resolver: "#{@ipKeyResolver}"
filter名称必须是RequestRateLimiter
redis-rate-limiter.replenishRate：允许用户每秒处理多少个请求
redis-rate-limiter.burstCapacity：令牌桶的容量，允许在一秒钟内完成的最大请求数
key-resolver：使用SpEL按名称引用bean
可以访问接口进行测试，这时候Redis中会有对应的数据：

127.0.0.1:6379> keys *
1) "request_rate_limiter.{localhost}.timestamp"
2) "request_rate_limiter.{localhost}.tokens"
大括号中就是我们的限流Key,这边是IP，本地的就是localhost

timestamp:存储的是当前时间的秒数，也就是System.currentTimeMillis() / 1000或者Instant.now().getEpochSecond()
tokens:存储的是当前这秒钟的对应的可用的令牌数量
Spring Cloud Gateway目前提供的限流还是相对比较简单的，在实际中我们的限流策略会有很多种情况，比如：

每个接口的限流数量不同，可以通过配置中心动态调整
超过的流量被拒绝后可以返回固定的格式给调用方
对某个服务进行整体限流（这个大家可以思考下用Spring Cloud Gateway如何实现，其实很简单）
……
当然我们也可以通过重新RedisRateLimiter来实现自己的限流策略，这个我们后面再进行介绍。

