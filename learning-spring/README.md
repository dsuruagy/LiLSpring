# Learning Spring with Spring Boot
01/10/2020

Course [link](https://www.linkedin.com/learning/learning-spring-with-spring-boot-2?contextUrn=urn%3Ali%3AlyndaLearningPath%3A5b101b04498e06fb6e2d8785)

This is the first project of the "Become a Spring Developer" learning path.

## Chapter: 1. Getting Started with Spring Boot
### Introduction to Spring

| Object                       | Definition                                                                       |
| ---------------------------- | -------------------------------------------------------------------------------- |
| POJO - Plain old Java object | From Spring prospective they are true objects, that have both state and behavior.|
| JavaBeans                   | Simple objects with only getters and setters.                                    |
| Spring beans                 | POJOs configured in the application context.                                     |
| DTO                          | JavaBeans used to move state between layers.                                    |

**_ApplicationContext_** is technically what we configure and serves as an IoC container. It's a wrapper for the BeanFactory and serves Spring beans to the runtime of the application.

### Examining a Spring Boot skeleton project

In Spring Initializr, choose Spring Web and Thymeleaf. 


## Chapter: 2. Data Access in Spring
### Welcome to Spring Data
**_Why Spring Data?_** Provides: common set of interfaces (repositories, based on the Repository Pattern) and naming convention for data access methods, with expected behavior and repository and data mapping convention.

Two key components: 
1) Repository interfaces that relies on the 
2) Entity objects for its template definition. 


## Chapter: 3. Service Tier
### Understanding dependency injection
#### Dependency Injection
- allows us to focus on contracts. We don't need to maintain the state of abstractions we've developed or use Spring's provided abstractions. We should focus only on the contract of our APIs. 
- We can develop business code only and leave construction to the container. Our actual code just consumes the abstraction and its API. 
- Build intermediate abstractions. This gives a very clean way to build and consume intermediate abstractions, very useful for aggregating data services behind common business processes. 
- Produce clean, reusable and maintainable code, because it need not be littered with code to construct objects and maintain its state. Instead, it is just the business logic that we care about. 

#### How Spring Does DI
- IoC container is configured by the developer (Java config, component scanning with annotations, auto-configuration or XML (not being used anymore) 
- Spring maintains handles to all configured objects constructed at startup. Spring beans are mostly instantiated as part of the startup life cycle of the Spring application, and the Bean Factory maintains the handle of all these beans.
- Spring serves Singletons to classes during construction. Most beans, at least the default behavior for beans, are singleton objects. It is important because we need to care about how the object state impacts all behavior of the class.
- Spring maintains the life cycle of beans. No objects instantiated by Spring will be garbage collected until the application quits. 
- Developer only configures ApplicationContext. The developer does not need to worry about other object  construction while using Spring. 

### Build a service abstraction
#### Spring annotations
- @Service is a stereotype of @Component. 
- Both allow component scanning to occur. 
- The reason to choose @Service is because we sometimes wants to set things like transaction boundaries or log boundaries, and it allows us to build aspects against this that won't apply to other classes within our stack.

## Chapter: 4. Web Pages with Spring
### Introduction to the controller
#### MVC
- Model is the data. 
- View is the visual display that is populated (usually in HTML and, often, using template engines like Thymeleaf, that uses template placeholders to the dynamic portion). 
- Controller is the business center of this pattern: selects the view, populates the model and then wires then both together. 
- Spring Controller is a Spring bean decorated with the @Controller
   - Annotated for the servlet mapping
   - Respond to incoming web requests
   -  Outputs a view or raw data. 
- Several Template Engines can be used with Spring. Often we're building web pages. Thymeleaf is the most popular. Provides a DSL for HTML, leaving raw HTML documents; Placeholders for dynamic data; Rendering engine allows for final product. 

## How to run this project

### Install PostgreSQL client
```
sudo apt install postgresql-client
```
### Configure Docker
```
sudo apt install docker
sudo groupadd docker
sudo usermod -aG docker dsuruagy
```
### Start Postgres script
```
cd bin
./start_postgres.sh
```
### Run application
```
mvn spring-boot:run
```
### Access the application

   http://localhost:8080
