在使用eureka的情况下，Zuul默认会将通过以服务名作为ContextPath的方式来创建路由映射。可以不配置Path

当我们这里构建的zuul应用启动并注册到eureka之后，服务网关会发现上面我们启动的两个服务eureka-client和eureka-consumer，这时候Zuul就会创建两个路由规则。每个路由规则都包含两部分，一部分是外部请求的匹配规则，另一部分是路由的服务ID。
针对当前示例的情况，Zuul会创建下面的两个路由规则：  
转发到 FEIGN-CONSUMER-1 服务的请求规则为：/feign-consumer-1/**  (**注意即便服务名为大写，url上 还是小写**)  
转发到 FEIGN-CONSUMER-2 服务的请求规则为：/feign-consumer-2/**  
最后，我们可以通过访问 2222 端口的服务网关来验证上述路由的正确性：  
访问：http://localhost:2222/feign-consumer-2/test ，该请求将最终被路由到 /feign-consumer-2 的/test 接口上。


