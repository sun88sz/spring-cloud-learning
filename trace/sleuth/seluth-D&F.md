
## zipkin server配置
### springboot 1.X + springcloud D


#### Server

依赖
```
<groupId>io.zipkin.java</groupId>
<artifactId>zipkin-autoconfigure-ui</artifactId>

<!--如果使用http作为通知方式-->
<dependency>
    <groupId>io.zipkin.java</groupId>
    <artifactId>zipkin-server</artifactId>
</dependency>

<!--如果使用mq作为通知方式-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-sleuth-zipkin-stream</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
</dependency>
```

Application
```
@SpringBootApplication
// stream 形式
@EnableZipkinStreamServer
// http 形式
@EnableZipkinServer
```

#### Client

```
spring:
  application:
    name: SLEUTH-APPLICATION-1
  zipkin:
    enabled: true

  sleuth:
    sampler:
      #  默认值为0.1f，现在为了测试设置100%采集
      percentage: 1
    # zipkkin dashboard的地址：通过真实IP地址访问
    # baseUrl: http://127.0.0.1:9411
    # 通过cloud-dashboard-zipkin注册到注册中心的服务名称访问
    # 配置如下：
    # baseUrl: http://cloud-dashboard-zipkin/

  # 如果使用mq作为通知方式
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```

pom.xml
```

<!--如果使用http作为通知方式-->
<dependency>
    <<groupId>org.springframework.cloud</groupId>
    <<artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>


<!--如果使用mq作为通知方式-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-sleuth-stream</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
</dependency>
```


### springboot 2.0 + springcloud F

#### Server
`@EnableZipkinStreamServer`
已被废弃

在spring boot2.0以上的版本中，官方不再支持使用自建Zipkin Server的方式进行服务链路追踪，而是直接提供了编译好的 jar 包来给我们使用

下载地址
https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/


#### Client
application.yml
```
spring:
  zipkin:
    sender:
      type: rabbit
```

```
<!--如果使用zipkin http作为通知方式-->
<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>

<!--zipkin使用 mq作为通知方式-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.amqp</groupId>
    <artifactId>spring-rabbit</artifactId>
</dependency>
```