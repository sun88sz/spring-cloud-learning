## 1. 概述
Spring Cloud Sleuth实现对Spring cloud 分布式链路监控  
本文介绍了和Sleuth相关的内容，主要内容如下：  

Spring Cloud Sleuth中的重要术语和意义：Span、Trance、Annotation  
Zipkin中图形化展示分布式链接监控数据并说明字段意义  
Spring Cloud集成Sleuth + Zipkin 的代码demo: Sleuth集成Zipkin, Zipkin数据持久化等  

## 2. 术语
### Span 
Span是基本的工作单元。Span包括一个64位的唯一ID，一个64位trace码，描述信息，时间戳事件，key-value 注解(tags)，span处理者的ID（通常为IP）。 
最开始的初始Span称为根span，此span中span id和 trace id值相同。

### Trance 
包含一系列的span，它们组成了一个树型结构

### Annotation 
用于及时记录存在的事件。常用的Annotation如下

- cs - Client Sent：客户端发送一个请求，表示span的开始
- sr - Server Received：服务端接收请求并开始处理它。(sr-cs)等于网络的延迟
- ss - Server Sent：服务端处理请求完成，开始返回结束给服务端。(ss-sr)表示服务端处理请求的时间
- cr - Client Received：客户端完成接受返回结果，此时span结束。(cr-sr)表示客户端接收服务端数据的时间
如果一个服务的调用关系如下： 
这里写图片描述
![image](http://daixiaoyu.com/images/distributed-tracing/dt003.png)
那么此时将Span和Trace在一个系统中使用Zipkin注解的过程图形化： 
这里写图片描述

每个颜色的表明一个span(总计7个spans，从A到G)，每个span有类似的信息

```
Trace Id = X
Span Id = D
Client Sent
```

此span表示span的Trance Id是X，Span Id是D，同时它发送一个Client Sent事件

## Header
sleuth会为请求的Header中增加实现跟踪需要的重要信息，主要有下面这几个
（更多关于头信息的定义我们可以通过查看 `org.springframework.cloud.sleuth.Span`的源码获取）：

- X-B3-TraceId：一条请求链路（Trace）的唯一标识，必须值
- X-B3-SpanId：一个工作单元（Span）的唯一标识，必须值
- X-B3-ParentSpanId:：标识当前工作单元所属的上一个工作单元，Root Span（请求链路的第一个工作单元）的该值为空
- X-B3-Sampled：是否被抽样输出的标志，1表示需要被输出，0表示不需要被输出
- X-Span-Name：工作单元的名称



## zipkin server配置

`@EnableZipkinStreamServer`
已被废弃

spring.zipkin.sender.type: rabbit