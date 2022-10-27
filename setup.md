# Setup For Urlink Project


### Components
- User, Link, Collection Service
- Eureka Discovery Service 
- Zipkin
- PostgreSQL

### SQL Configuration

```sql
-- user
CREATE ROLE azureapp WITH
	LOGIN
	NOSUPERUSER
	NOCREATEDB
	NOCREATEROLE
	INHERIT
	NOREPLICATION
	CONNECTION LIMIT -1
	PASSWORD 'xxxxxx';

-- db
CREATE DATABASE azuredb
    WITH 
    OWNER = azureapp
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;
```



## Config Folders

```bash
mkdir src/main/resources
touch src/main/resources/application.yml
```

### User Service 

```yml
server:
  port: 8080

spring:

  application:
    name: USER-SERVICE

  datasource:
    url: jdbc:postgresql://localhost:5432/azuredb
    username: azureapp
    password: [password]

  jpa:
    hibernate.ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

eureka:
  client:
    registry-with-eureka: true
    fetch-registry: true
    service-url:
      default-zone: http://localhost:8761/eureka/
    instance:
      hostname: localhost
```

### Collection Service 

```yml
server:
  port: 8081

spring:

  application:
    name: COLLECTION-SERVICE

  datasource:
    url: jdbc:postgresql://localhost:5432/azuredb
    username: azureapp
    password: password

  jpa:
    hibernate.ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

eureka:
  client:
    registry-with-eureka: true
    fetch-registry: true
    service-url:
      default-zone: http://localhost:8761/eureka/
    instance:
      hostname: localhost
```

### Link Service

```yml
server:
  port: 8082

spring:

  application:
    name: LINK-SERVICE

  datasource:
    url: jdbc:postgresql://localhost:5432/azuredb
    username: azureapp
    password: password

  jpa:
    hibernate.ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

eureka:
  client:
    registry-with-eureka: true
    fetch-registry: true
    service-url:
      default-zone: http://localhost:8761/eureka/
    instance:
      hostname: localhost
```

### Cloud Gateway

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
            - Path=/api/v1/user/**
          filters:
            - name: CircuitBreaker
              args:
                name: USER-SERVICE
                fallbackuri: forward:/userServiceFallBack
        - id: COLLECTION-SERVICE
          uri: lb://COLLECTION-SERVICE
          predicates:
            - Path=/api/v1/collection/**
          filters:
            - name: CircuitBreaker
              args:
                name: COLLECTION-SERVICE
                fallbackuri: forward:/collectionServiceFallBack
        - id: LINK-SERVICE
          uri: lb://LINK-SERVICE
          predicates:
            - Path=/api/v1/link/**
          filters:
            - name: CircuitBreaker
              args:
                name: LINK-SERVICE
                fallbackuri: forward:/linkServiceFallBack
```