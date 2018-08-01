Endpoints
Endpoints是actuator非常重要的部分,用来监视程序,和应用交互(如检查信息,)


启用actuator
如果需要使用这些功能,只需要在代码中引入actuator的start即可.
```
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

ID	|描述	|是否需要鉴权
---|---|---
actuator	|为其他端点提供“发现页面”。要求Spring HATEOAS在classpath路径上。     |需要
auditevents	|陈列当前应用程序的审计事件信息。	                                    |需要
autoconfig	|展示自动配置信息并且显示所有自动配置候选人以及他们“被不被”应用的原因。	|需要
beans	    |显示应用程序中所有Spring bean的完整列表。	                            |需要
configprops	|显示所有配置信息。	                                                |需要
dump	    |dump所有线程。	                                                    |需要
env	        |陈列所有的环境变量。	                                                |需要
flyway	    |Shows any Flyway database migrations that have been applied.	    |需要
health	    |显示应用程序运行状况信息	                                            |不需要
info	    |显示应用信息。	                                                    |不需要
loggers	    |显示和修改应用程序中的loggers配置。	                                |需要
liquibase	|显示已经应用的任何Liquibase数据库迁移。	                            |需要
metrics	    |显示当前应用程序的“指标”信息。	                                    |需要
mappings	|显示所有@RequestMapping的url整理列表。	                            |需要
shutdown	|关闭应用（默认情况下不启用）。	                                    |需要
trace	    |显示跟踪信息（默认最后100个HTTP请求）。	                            |需要


在application.yml中配置
```
management:
  security:
    #是否启用安全
    enabled: true
endpoints:
  shutdown:
    #开启shutdown端点
    enabled: true
```

访问 http://127.0.0.1:10101/metrics