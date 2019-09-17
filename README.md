# platform
platform是基于`Spring Cloud`微服务化开发平台，其具有统一授权认证的管理系统，其中包含具备用户管理、资源权限管理、网关API管理等多个模块，支持多业务系统并行开发，可以作为后端服务的开发脚手架。核心技术采用Eureka、Fegin、Ribbon、Zuul、Hystrix、Security、JWT Token、Mybatis等主要框架和中间件。 

------
# 架构详解
使用Eureka实现服务注册、zuul/rebbion实现代理和网关控制、Hystrix实现避免服务间的“雪蹦”、Security/JWT Token实现服务间权限验证和用户访问权限控制、Spring Boot Admin实现监控各服务的运行状态
#### 服务注册与调用
基于Eureka来实现的服务注册与调用，在Spring Cloud中使用Feign, 做到使用HTTP请求远程服务时能与调用本地方法一样的编码体验，开发者完全感知不到这是远程方法，更感知不到这是个HTTP请求。
#### 负载均衡
使用Spring Cloud系列的zuul和rebbion将服务保留的rest进行代理和网关控制，进行正常的网关管控和负载均衡。
#### 熔断机智
使用Hystrix的作为熔断器，在服务的分布时避免服务之间的调用“雪蹦”。
#### 服务与用户权限控制
使用Security和JWT Token控制服务间权限验证与用户访问权限控制。
阿是打发
#### 熔断机智监控
使用Spring Boot Admin来监控各个独立Service的运行状态；并利用Hystrix Dashboard来实时查看接口的运行状态和调用频率等。

------
# 项目结构
```
├─platform
│  │  
│  ├─system
│  │  ├─system-center---------------注册中心 
│  │  ├─system-common---------------系统级公共工具包 
│  │  ├─system-config---------------配置中心
│  │  ├─system-monitor--------------监控中心
│  │  ├─system-zuul-gate------------系统级网关过滤包
│  ├─auth
│  │  ├─auth-bs---------------------认证模块
│  │  ├─auth-client-----------------认证模块对其他模块提供的服务对应的客户端
│  │  ├─auth-dao--------------------认证模块 DAO层模块
│  │  ├─auth-entity-----------------实体
│  │  ├─auth-model------------------模型
│  │  ├─auth-common-----------------公共
│  │  ├─auth-service----------------接口
│  │  ├─auth-service-impl-----------接口实现
│  ├─api
│  │  ├─api-gate--------------------API网关模块(提供三方应用访问入口接口)
│  ├─user
│  │  ├─user-bs---------------------用户模块
│  │  ├─user-client-----------------用户模块对其他模块提供的服务对应的客户端
│  │  ├─user-dao--------------------用户模块 DAO层模块
│  │  ├─user-entity-----------------实体
│  │  ├─user-model------------------模型
│  │  ├─user-common-----------------公共
│  │  ├─user-service----------------接口
│  │  ├─user-service-impl-----------接口实现
│  ├─rabbitmq
│  │  ├─rabbitmq-bs-----------------消息模块
│  │  ├─rabbitmq-client-------------消息模块对其他模块提供的服务对应的客户端
│  │  ├─rabbitmq-dao----------------消息模块 DAO层模块
│  │  ├─rabbitmq-entity-------------实体
│  │  ├─rabbitmq-model--------------模型
│  │  ├─rabbitmq-common-------------公共
│  │  ├─rabbitmq-service------------接口
│  │  ├─rabbitmq-service-impl-------接口实现
│  │  ├─rabbitmq-lisener------------消息监听
```

------
# 模块说明
各模块端口说明
模块服务						端口						说明
system-center				9001					注册中心
system-config				9002					配置中心
system-monitor				9003					监控模块
auth-bs						9020					认证模块
user-bs						9010					用户模块
api-gate					9030					API模块网关

------------
# 功能简介
1. 用户管理
2. 角色管理
3. 资源管理
4. 字典管理
5. 操作日志
6. 监控管理（待完善）
7. 消息管理（待完善）
8. 代码生成

-----
# 启动指南
# 部署须知
- mysql，redis
- jdk1.8
- lombok插件

-----
# 运行步骤，依次运行main类
注：1、2、3步骤必须按顺序依次启动, 其他在1、2、3启动之后, 其余启动顺序无影响
1、CenterBootstrap（system-center）			------注册中心
2、ConfigBootstrap（system-config）			------配置中心
3、AuthBootstrap（auth-bs）					------认证
4、UserBootstrap(user-bs)					------用户服务
5、ApiGateBootstrap(api-gate)				------API网关服务模块 
