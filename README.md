# Azure hackathon submission

## Overview of My Submission

### Project Urlink

#### Product Overview

Surfing the internet, we come across resources that we might need for later use or maybe store them for a current project we are working on. This is where project Urlink comes in. It helps the user create multiple collections which can store multiple URLs with description for later access.

#### Technical Overview
- The project is created using Spring Boot, a framework for creating back-end and microservices using Java, with - MySQL as database.
- The project is deployed to Azure Spring Cloud and connected to Azure MySQL single server database
- The code is divided into 5 modules and 3 micro-services, 1 cloud-gateway, 1 shared module containing models, - DTOs etc.
- All microservice discover each other using Eureka service registry and internally requests are load balanced - going from one micro-service to another.
- All microservices perform database operations using Spring Data JPA library.
- All micro-service implemented according to Open-API specification and documented using swagger.
- All database models are abstracted from the user end using Data Transfer Objects ( DTOs )
- You can send HTTP requests to individual micro-services but to make our infrastructure more resilient, we - implemented cloud gateway and circuit breaker for all requests to traverse through it and in-case of any - failure, handle the fallback and inform the user about the same.
- Configurations are loaded into the system using the config server using git and azure service binding.
- Service Registry and Cloud Config server are integrated and fully-managed by Azure spring cloud.
- Modules are packaged in .jar files and deployed to azure spring cloud from azure devops build pipeline
- For distributed tracing and logging, we added zipkin and sleuth libraries which integrate with the AppInsights dashboard for Azure

### On Azure

![azure-deploy drawio](https://user-images.githubusercontent.com/49728410/156921191-1a376187-3a0f-4f35-afb6-a8094712d647.png)

#### ER Diagram

![entity diagram](https://user-images.githubusercontent.com/49728410/156921205-3950a762-b26b-4496-b4b6-75d7cfb68490.png)

### Submission Category: Java Jackpot
### Link to Code on GitHub

- Code : https://github.com/dev117uday/azure-dev-hackathon
- Azure devops : https://dev.azure.com/udayyadav117/azure-dev-hackathon/_build

### Additional Resources / Info

![azure-hack-service](https://user-images.githubusercontent.com/49728410/156921218-2bfdd235-690e-43bb-a3ed-7457a646ee05.png)
![all resource](https://user-images.githubusercontent.com/49728410/156921225-84c47413-7125-42c2-936d-421c6fb4aeda.png)
![mysql](https://user-images.githubusercontent.com/49728410/156921228-73ce7818-75d5-447f-b3cc-34d28d647571.png)
![spring-cloud](https://user-images.githubusercontent.com/49728410/156921230-bb06adf2-802a-4bf2-a197-1e3bbdd22f46.png)
![2022-03-05_20-03](https://user-images.githubusercontent.com/49728410/156921254-9cde4c9d-9bed-4217-9cea-28c9de256c16.png)
![2022-03-05_20-03_1](https://user-images.githubusercontent.com/49728410/156921256-63296a01-933b-4365-9aa5-52b6b8f6c32a.png)
![2022-03-05_20-03_2](https://user-images.githubusercontent.com/49728410/156921257-58393c36-4535-4087-8381-72b226fa0456.png)
![2022-03-05_20-13](https://user-images.githubusercontent.com/49728410/156921258-ee9f01a2-a145-4979-8a36-4a35a5cf8fab.png)
![2022-03-05_20-14](https://user-images.githubusercontent.com/49728410/156921260-1f329238-533c-4190-b3b6-0b61d5806a7f.png)
![2022-03-05_20-35_1](https://user-images.githubusercontent.com/49728410/156921262-72c77862-c4eb-439b-802e-9c5da3496d81.png)
