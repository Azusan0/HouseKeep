# 云服务器与 Nacos 配置说明

本文档用于说明 `duolai-clean` 项目连接云服务器中间件时应该如何配置 Nacos，以及当前控制台报错的原因。

## 1. 当前报错原因

控制台核心报错是：

```text
Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
Reason: Failed to determine a suitable driver class
```

意思是：`housekeeping-service` 启动时需要 MySQL 数据源，但 Spring Boot 没有读取到 `spring.datasource.url`。

日志里还有这两行：

```text
[Nacos Config] config[dataId=common.yml, group=DEFAULT_GROUP] is empty
[Nacos Config] config[dataId=housekeeping-dev.yml, group=DEFAULT_GROUP] is empty
```

所以根因基本可以确定为：Nacos 中 `common.yml` 或 `housekeeping-dev.yml` 没有正确发布配置，导致数据库连接配置没有被应用读取到。

## 2. 当前项目配置加载规则

项目里这些服务会从 Nacos 读取配置：

| 服务 | 本地配置文件 | 应用名 | 本地端口 | Nacos Data ID |
| --- | --- | --- | --- | --- |
| gateway | `gateway/src/main/resources/application.yml` | `gateway` | `11500` | `gateway-dev.yml` |
| foundation-service | `foundation/foundation-service/src/main/resources/application.yml` | `foundation` | `11503` | `foundation-dev.yml`、`common.yml` |
| housekeeping-service | `housekeeping/housekeeping-service/src/main/resources/application.yml` | `housekeeping` | `11509` | `housekeeping-dev.yml`、`common.yml` |

当前 profile 是：

```yaml
spring:
  profiles:
    active: dev
```

所以 Nacos 的 Data ID 必须带 `-dev.yml`，比如 `housekeeping-dev.yml`。

## 3. 云服务器信息

服务器公网 IP：

```text
110.42.253.116
```

本机 SSH 私钥路径：

```powershell
C:\Users\Administrator\Desktop\character\OnSeaService.pem
```

Windows PowerShell 连接示例：

```powershell
ssh -i "C:\Users\Administrator\Desktop\character\OnSeaService.pem" root@110.42.253.116
```

如果你的云服务器不是 `root` 用户，把 `root` 改成实际用户名，例如 `ubuntu`。

注意：私钥文件不要上传到 Git，也不要复制到 Nacos。

## 4. Nacos 访问地址

浏览器打开：

```text
http://110.42.253.116:8848/nacos
```

默认账号密码通常是：

```text
nacos / nacos
```

进入后选择：

```text
配置管理 -> 配置列表 -> DEFAULT_GROUP
```

需要创建或修改以下 Data ID：

```text
common.yml
housekeeping-dev.yml
foundation-dev.yml
gateway-dev.yml
```

格式都选择：

```text
YAML
```

## 5. common.yml 推荐配置

如果你是在本地 IntelliJ 启动 Java 服务，连接云服务器上的 MySQL、Redis、Elasticsearch、RocketMQ、XXL-JOB、Seata，`common.yml` 可以写公网 IP：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://110.42.253.116:3306/${mysql.db-name}?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai&stringtype=unspecified
    username: root
    password: 1234
  data:
    redis:
      host: 110.42.253.116
      port: 6379
  elasticsearch:
    uris: http://110.42.253.116:9200
    connection-timeout: 6s
    socket-timeout: 10s

mybatis-plus:
  configuration:
    default-enum-type-handler: com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler
  page:
    max-limit: 1000
  global-config:
    field-strategy: 0
    db-config:
      logic-delete-field: isDeleted
      id-type: assign_id

xxl-job:
  enable: true
  port: 9999
  access-token: default_token
  admin:
    address: http://110.42.253.116:9080/xxl-job-admin
  executor:
    appName: ${spring.application.name}
    port: ${xxl-job.port}
    log-retention-days: 30
    logpath: d:/data/applogs/xxl-job/jobhandler

rocketmq:
  namesrv:
    address: 110.42.253.116:9876
  producer:
    group: ${spring.application.name}
  consumer:
    group:
      prefix: ${spring.application.name}

seata:
  data-source-proxy-mode: AT
  registry:
    type: nacos
    nacos:
      server-addr: 110.42.253.116:8848
      group: DEFAULT_GROUP
      application: seata-server
      username: nacos
      password: nacos
  tx-service-group: duolai-clean-seata
  service:
    vgroup-mapping:
      duolai-clean-seata: default

redisson:
  config:
    singleServerConfig:
      address: "redis://${spring.data.redis.host}:${spring.data.redis.port}"
      idleConnectionTimeout: 10000
      connectTimeout: 10000
```

这里有两个容易踩坑的点：

1. `redisson.config.singleServerConfig.address` 要引用 `${spring.data.redis.host}`，不是 `${spring.redis.host}`。当前项目使用的是 Spring Boot 3，Redis 配置路径是 `spring.data.redis`。
2. `xxl-job.executor.port` 引用了 `${xxl-job.port}`，所以必须先定义 `xxl-job.port`。

如果 Java 服务也部署在同一台云服务器上，建议把中间件地址改成内网地址或本机地址，例如：

```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/${mysql.db-name}?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai&stringtype=unspecified
  data:
    redis:
      host: 127.0.0.1
  elasticsearch:
    uris: http://127.0.0.1:9200

rocketmq:
  namesrv:
    address: 127.0.0.1:9876
```

如果中间件是 Docker Compose 部署，也可以使用容器网络里的服务名，例如 `mysql`、`redis`、`elasticsearch`、`rocketmq-namesrv`，具体取决于你的 `docker-compose.yml`。

## 6. housekeeping-dev.yml 推荐配置

`housekeeping-service` 本地已经定义了：

```yaml
mysql:
  db-name: housekeeping
```

但为了避免 Nacos 配置为空导致排查困难，建议在 Nacos 的 `housekeeping-dev.yml` 至少放这些服务专属配置：

```yaml
mysql:
  db-name: housekeeping

duolai:
  clean:
    jwt-key: duolai-clean-operation-secret

feign:
  enable: true
```

如果后续登录、第三方接口需要真实密钥，再补充对应配置。

## 7. foundation-dev.yml 推荐配置

`foundation-service` 主要使用 Redis、短信、地图、OSS、微信等能力。可以先放最小配置：

```yaml
feign:
  enable: true

swagger:
  enable: true

ali:
  oss:
    enable: false

amap:
  enable: false

tencent:
  wechat:
    enable: false
```

如果要启用第三方能力，再把 `enable` 改成 `true` 并补齐密钥：

```yaml
ali:
  oss:
    enable: true
    endpoint: your-oss-endpoint
    access-key-id: your-access-key-id
    access-key-secret: your-access-key-secret
    bucket-name: your-bucket-name

amap:
  enable: true
  key: your-amap-key

tencent:
  wechat:
    enable: true
    app-id: your-app-id
    secret: your-secret
```

## 8. gateway-dev.yml 推荐配置

`gateway` 只导入 `gateway-dev.yml`，不导入 `common.yml`，所以网关路由和 JWT 白名单要放到 `gateway-dev.yml`。

示例：

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: foundation
          uri: lb://foundation
          predicates:
            - Path=/foundation/**
        - id: housekeeping
          uri: lb://housekeeping
          predicates:
            - Path=/housekeeping/**

duolai:
  clean:
    token-key:
      "1": duolai-clean-user-secret
      "2": duolai-clean-worker-secret
      "3": duolai-clean-operation-secret
    access-path-white-list:
      - /housekeeping/operation/login
      - /foundation/outer/sms-code
```

注意：网关代码当前只用完全匹配判断白名单：

```java
applicationProperties.getAccessPathWhiteList().contains(path)
```

所以白名单里要写完整路径，不能写 `/housekeeping/**` 这种通配符。

## 9. 本地 application.yml 是否需要改

当前项目本地配置已经把 Nacos 地址改成了：

```yaml
spring:
  cloud:
    nacos:
      discovery:
        server-addr: 110.42.253.116:8848
      config:
        server-addr: 110.42.253.116:8848
        context-path: /nacos
```

所以本地一般不用再改代码。重点是确认 Nacos 上配置不为空。

如果你本地还有旧配置 `192.168.150.110`，需要改成：

```yaml
server-addr: 110.42.253.116:8848
```

## 10. 云服务器安全组和防火墙

本地 IntelliJ 直连云服务器中间件时，云服务器安全组至少要允许这些端口：

| 端口 | 用途 |
| --- | --- |
| 22 | SSH |
| 8848 | Nacos |
| 3306 | MySQL |
| 6379 | Redis |
| 9200 | Elasticsearch |
| 9876 | RocketMQ NameServer |
| 9080 | XXL-JOB Admin |
| 8091 | Seata，按实际部署端口调整 |

安全建议：

1. 学习环境可以临时开放给自己的公网 IP。
2. 不建议把 MySQL、Redis、Elasticsearch 长期暴露给整个公网。
3. Redis 如果没有密码，尤其不要开放 `6379` 给 `0.0.0.0/0`。
4. 生产或长期使用时，建议用 VPN、内网、安全组白名单或 SSH 隧道。

## 11. 快速验证步骤

### 11.1 验证 Nacos 配置

打开：

```text
http://110.42.253.116:8848/nacos
```

确认这些配置存在且内容不是空的：

```text
common.yml
housekeeping-dev.yml
foundation-dev.yml
gateway-dev.yml
```

### 11.2 验证本地能访问 Nacos

PowerShell：

```powershell
Test-NetConnection 110.42.253.116 -Port 8848
```

看到 `TcpTestSucceeded : True` 表示端口通。

### 11.3 验证 MySQL 端口

PowerShell：

```powershell
Test-NetConnection 110.42.253.116 -Port 3306
```

### 11.4 重新启动 housekeeping-service

启动后重点看日志里是否还出现：

```text
[Nacos Config] config[dataId=common.yml, group=DEFAULT_GROUP] is empty
Failed to configure a DataSource
```

如果不再出现，说明数据库配置已经读取成功。

## 12. 常见问题

### 12.1 Nacos 明明有配置，日志还是 empty

检查这几项：

1. Data ID 是否完全一致，例如必须是 `common.yml`，不是 `common.yaml`。
2. Group 是否是 `DEFAULT_GROUP`。
3. 配置格式是否选择 `YAML`。
4. 是否点击了“发布”。
5. 项目连接的 Nacos 地址是否就是 `110.42.253.116:8848`。

### 12.2 MySQL 连接失败

如果报：

```text
Communications link failure
```

通常是端口不通、MySQL 没启动、MySQL 没监听公网地址，或安全组没开放 `3306`。

如果报：

```text
Access denied for user 'root'
```

通常是账号密码错误，或 MySQL 没授权 `root` 从当前客户端 IP 登录。

### 12.3 Redis 或 Redisson 报占位符错误

检查 `common.yml` 里是否写成了：

```yaml
address: "redis://${spring.redis.host}:${spring.redis.port}"
```

应该改成：

```yaml
address: "redis://${spring.data.redis.host}:${spring.data.redis.port}"
```

### 12.4 Elasticsearch 地址格式

建议写完整协议：

```yaml
spring:
  elasticsearch:
    uris: http://110.42.253.116:9200
```

不要只写：

```yaml
uris: 110.42.253.116:9200
```

### 12.5 配置文件乱码

项目里部分中文注释显示为乱码，不影响启动。后续如果要修复，建议统一用 UTF-8 保存 `application.yml` 和 Java 文件。
