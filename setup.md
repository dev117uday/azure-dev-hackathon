# Configuration for all microservices

### Generate Folder structure

```sh
mkdir department/src/main/resources
mkdir user/src/main/resources
mkdir service-regsitry/src/main/resources
mkdir cloud-gateway/src/main/resources
mkdir hystrix-dashboard/src/main/resources
mkdir cloud-config-server/src/main/resources

touch department/src/main/resources/application.yml
touch user/src/main/resources/application.yml
touch service-regsitry/src/main/resources/application.yml
touch cloud-gateway/src/main/resources/application.yml
touch hystrix-dashboard/src/main/resources/application.yml
touch cloud-config-server/src/main/resources/application.yml

touch department/src/main/resources/bootstrap.yml
touch user/src/main/resources/bootstrap.yml
touch cloud-gateway/src/main/resources/bootstrap.yml
touch hystrix-dashboard/src/main/resources/bootstrap.yml
```

### Setup PostgreSQL

```sql
CREATE ROLE springmicroapp WITH
	LOGIN
	NOSUPERUSER
	NOCREATEDB
	NOCREATEROLE
	INHERIT
	NOREPLICATION
	CONNECTION LIMIT -1
	PASSWORD 'xxxxxx';

CREATE DATABASE springmicro
    WITH 
    OWNER = springmicroapp
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;
```

### Eureka Service Registry

- Select : `spring-cloud-netflix Eureka Server` in spring initializer
- Annotate the main class with `@EnableEurekaServer`

- `application.yml`

```yml
server:
  port: 8761

eureka:
  client:
    fetchRegistry: 'false'
    registerWithEureka: 'false'
```


### Cloud Gateway Registry 

- For cloud gateway : `Spring Boot Actuator` and `Gateway [spring cloud routing]`
- Mark the main class with `@EnableEurekaClient`
- Add cicuitbreaker
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-circuitbreaker-reactor-resilience4j</artifactId>
    <version>2.1.0</version>
</dependency>
```

- `application.yml`

```yml
server:
  port: 9191

spring:

  main:
    web-application-type: reactive

  application:
    name: API-GATEWAY

  cloud:
    gateway:
      routes:
        - id: USER-SERVICE
          uri: lb://USER-SERVICE
          predicates:
            - Path=/users/**
          filters:
            - name: CircuitBreaker
              args:
                name: USER-SERVICE
                fallbackuri: forward:/userServiceFallBack
        - id: DEPARTMENT-SERVICE
          uri: lb://DEPARTMENT-SERVICE
          predicates:
            - Path=/departments/**
          filters:
            - name: CircuitBreaker
              args:
                name: DEPARTMENT-SERVICE
                fallbackuri: forward:/departmentServiceFallBack

hystrix:
  command:
    fallbackcmd:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 4000

management:
  endpoints:
    web:
      exposure:
        include: "*"
```

- `bootstrap.yml`

```yml
spring:
  cloud:
    config:
      enabled: true
      uri: http://localhost:9296
```

### Hystrix Configuration

- `application.yml`

```yml
server:
  port: 9295

spring:
  application:
    name: HYSTRIX-DASHBOARD

hystrix:
  dashboard:
    proxy-stream-allow-list: "*"
```

- `bootstrap.yml`

```yml
spring:
  cloud:
    config:
      enabled: true
      uri: http://localhost:9296
```

### Cloud Config Configuration

```yml
server:
  port: 9296

spring:
  application:
    name: CONFIG-SERVER
  cloud:
    config:
      server:
        git:
          uri: https://github.com/dev117uday/config-server
          clone-on-start: true
```

### User Service

- `application.yml`
```yml
spring:
  application:
    name: USER-SERVICE

  zipkin:
    base-url: http://[host]:9411

  datasource:
    url: jdbc:postgresql://[host]:5432/[DB]
    username: [USERNAME]
    password: [PASSWORD]

  jpa:
    hibernate.ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

server:
  port: 8001
```

- `bootstrap.yml` for cloud config server

```yml
spring:
  cloud:
    config:
      enabled: true
      uri: http://[host]:9296
```

