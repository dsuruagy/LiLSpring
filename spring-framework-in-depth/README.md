# Spring Framework In Depth

## Chapter: 2. Configuring the ApplicationContext

### Introduction to the ApplicationContext

The _Application Context_, and more specifically The _BeanFactory_, is one of the most important components in the spring framework.

The _Application Context_:
* Acts as the heart of the Spring Application. 
* Is the central element that you deal with from the Spring framework, when developing an application. 

* Our classes should seldom, if ever have imports from the Spring framework except in annotations. However, the _Application Context_ and the entry point of your application is an exception.

* Encapsulates the _BeanFactory_, that is the IoC container itself. 
    * Provides a user access to the _BeanFactory_ under controlled situations. 
    * It provides the point for metadata for bean creation. No matter the mechanism we use to configure our beans, the _Application Context_ serves to take that configuration and allow the framework to use it to build the IoC container itself.
    * This provides all the facilities for injection of beans at startup and run time. 
    * While most beans are singletons and injected at startup, there are other types of beans that get handled differently. However, all the injection is handled by the BeanFactory.
    * Most of the developer's interaction with Spring is actually configuring the IoC container. We do use abstractions provided by Spring, but they are just pre-configured facades on top of repetitive processes for the most part. Really, using Spring is more about configuration, at least at its core. 
    * Beans of type singleton will always be referenced in the _BeanFactory_. Even if a class goes out of scope in the main runtime, the _BeanFactory_ will always have a handle. This also means that the instance object is injected everywhere it's used. So you need to keep that in account, when storing state in those objects. Attributes for these classes must be handled carefully. Beans that aren't singletons are handled differently.

* Ensures that the beans are created in appropriate order. 
    * This can be of concern when a central object manages all the dependencies in an application. It must be done in proper order to ensure the dependencies are available when needed for the construction of objects. 
    * Spring handles that through mechanisms within the _BeanFactory_ and the _Application Context_.

* Multiple _Application Contexts_, can bring a little more challenge to your application, and needs to be addressed. A Spring application can have more than one _Application Context_. It will always have at least one, but in certain situations there can exist multiple, like with Web containers.

How to use the application context for dependency injection:

1) Initially, we had an Application class that instantiates services directly. For example:
```
public class Application {

    public static void main(String[] args) throws Exception {
        GreetingService greetingService = new GreetingService("Hello");
        TimeService timeService = new TimeService(true);
        OutputService outputService = new OutputService(greetingService, timeService);
```
2) We could introduce the ApplicationContext, through a new configuration class:
```
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);
        OutputService outputService = context.getBean(OutputService.class);
```
The configuration class is something like this:
```
@Configuration
public class ApplicationConfig {
    @Value("Olá,")
    private String greeting;

    @Autowired
    private GreetingService greetingService;

    @Autowired
    private TimeService timeService;

    @Bean
    public GreetingService greetingService() {
        return new GreetingService(greeting);
    }
    
    @Bean
    public TimeService timeService() {
        return new TimeService(true);
    }

    @Bean
    public OutputService outputService() {
        return new OutputService(greetingService, timeService);
    }
}
```

### Work with the environment

Most applications cannot be deployed to just one data center anymore, and robust configuration patterns are needed to support this. The use of environment variables and properties is one of the most common ways of injecting data into a running application to Flex by things like a data center or environment. 

Spring provides an environment abstraction that allows you to build applications that flex configuration based on environment variables. The environment within Spring is populated by default with all the system environment variables at the run time of the application. 

```
@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfig {
    // These properties can be overridden at IDE or OS level
    @Value("${app.greeting}")
    private String greeting;
```
application.properties:
```
app.greeting=Hello
```

This environment construct is also populated and can be supplemented by properties via run time or by properties files, among other things. This allows you to provide a set of default configuration files that can be overridden at runtime, and there's a very detailed hierarchy of like 15 layers deep in the Spring documentation on the order in which these values, when they share a key, will be overridden. 

This is a very powerful tool, especially in a cloud native world. It is also useful in traditional applications when dealing with multiple environments. You can use a mixture of properties and environment variables to manipulate the configuration as you migrate from Dev to Test, or from data center to data center. This is how you could manage things like URLs of other services, log levels, and various other runtime configurations within a microservices' environment. 

### Profiles
Profiles that can be used with VM option -Dspring.profiles.active=prod, for example:
```
import org.springframework.context.annotation.Profile;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfig {
...
    @Bean
    @Profile("!dev")
    public TimeService timeService() {
        return new TimeService(true);
    }

    @Bean
    @Profile("dev")
    public TimeService timeService12() {
        return new TimeService(false);
    }
...
}
```

### Spring Expression Language
Adding Spring Expression Language - SPEL to check which environment profile is active.
```
@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    @Value("#{new Boolean(environment['spring.profiles.active']!='dev')}")
    private boolean is24;

    @Bean
    public TimeService timeService() {
        return new TimeService(is24);
    }
...
}
```

### Bean scopes

This is a concept that is very **important** to understand.

#### Singleton Scope
* The most common bean scope
* The singleton is the default scope of all beans defined for the application context. If you did not specify a specific scope, you get a singleton, you get one instance of the object per context definition.
* Need to be careful with state because you have one instance of the class all state essentially becomes available to all instances of the object so even though there's only one instance, every class that has it as a dependency could potentially have access to that state.

#### Prototype
* A prototype bean is one that you get a new instance, every time it's referenced.
* Once the instance is no longer needed or referenced, it becomes available for garbage collection. 
* A definition of the prototype is stored in the IoC container. When an instance is needed it is created based on the definition Spring then hands that instance over and releases its own handle.
* A prototype bean is _never stored as an instantiated object_ in the IoC container. These bean types are very useful for transient data, or interface types that can flex based on application state.

#### Session beans 
* Very similar to prototype beans, they only apply however in web environments, you get one instance of the bean per user session. This allows you to keep session data separate from other sessions when needed
* Like a prototype bean the definition is stored in the IoC container, but _the instance itself is never stored_, and when the session goes out of scope, that bean is available for garbage collection. 

#### Request Scope Beans
* Very similar to session and prototype scoped beans. Once again these only apply to web environments, and you get one instance of the bean per request into the application, so instead of session which is multiple requests and responses, this is one per request
* The definition is once again stored in the IoC container, but the instance is not and once that request is done, that class is available for garbage collection. 

### Proxies
Proxies are essentially aspected behavior that is applied to your classes by the framework under various conditions. In Spring, everything is a proxy. And this comes with some considerations. This behavior was added in Spring 4.0. All of your classes, regardless of their annotations, get wrapped with at least one proxy. Proxies are used to add behavior to your class. Many of the proxies have specific purposes that enable you to write code cleaner, things like transactional boundaries. Spring uses both JDK and CGLib-based proxies in its operation. 

Some considerations when using proxies:
* Proxies can only apply their behavior when a call goes through to proxy. Private methods don't get exposed via the proxy, so they will not have behavior added to them. 
* For the same reason, internal calls don't get proxy behavior either. This lack of behavior as mentioned can be a source of bugs. 
* For example, with some JDBC template code using transactions, internal calls will not work because the rollback on exception wasn't handled because it wasn't part of the proxy because it was an internal method. Don't call reference methods on the same class and expect them to have behavior added to them by the proxy.

## Chapter: 3. Component Scanning
With Spring Boot, the auto-configuration is partially achieved through component scanning mixed with conditional configurations. In fact for many classes external configuration isn't needed as component scanning can solve the configuration.


