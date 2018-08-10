

## Zuul 能做的事情
签名校验  
权限校验  
请求限流  
统一异常处理  
日志处理


## Zuul的Header安全
### 敏感的Header设置
在同一个系统中微服务之间共享Header,但是某些时候尽量防止让一些敏感的Header外泄。因此很多场景下，需要通过为路由指定一系列敏感的Header列表。例如：
```
zuul:
  routes:
    abc:
      path: /provider/**
      service-id: microservice-provider-user
      sensitiveHeaders:Cookie,Set-Cookie,Authorization
      url: https://downstream
```

### 忽略Header
被忽略的Header不会被传播到其他的微服务去。其实敏感的Heade最终也是走的这儿

```
zuul:
  ignored-headers: Header1,Header2
```

默认情况下，ignored-headers是空的