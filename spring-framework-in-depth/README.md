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
Auto-configuration, which happens when using Spring Boot, can be achieved through Component Scanning. 

* The _@Component_ is the root annotation that indicates that the class should be loaded into the BeanFactory, but we can use one of its stereotypes, like _@Service_ which can be used to write aspects to add behavior to classes based on type. 

* It scans the base package and its sub-packages, loading configuration automatically for each bean it finds. It uses other annotations to direct the IOC container to build the dependency graph.
```
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.frankmoley.lil.fid")
public class ApplicationConfig {

}
```
  * If a dependency is required it should be set on the constructor. If it's not, should be on the setter.
  * Dependency injection is achieved mainly through the annotation _@Autowired_. This instructs the IOC container to inject a bean into the needed value at that point. While you can put it on a class attribute, you should only set it on methods.
  * If you have more than one bean of the same type you can use the _@Qualifier_ annotation to inject a specific version by name.
  * Properties or constants can be injected using the _@Value_ annotation to instruct the IOC container to put a value into a variable for use elsewhere.
```
@Service
public class OutputService {
@Value("${app.name}")
private String name;

    private final GreetingService greetingService;
    private final TimeService timeService;

    /**
     * Not default constructor used to instantiate OutputService, that requires two arguments.
     * As there is no default constructor, the Autowired annotation is optional here, because
     * Spring knows that it should auto wire dependencies to create a new instance.
     *
     * It was used to make it more explicit here.
     * @param greetingService
     * @param timeService
     */
    @Autowired
    public OutputService(GreetingService greetingService, TimeService timeService){
        this.greetingService = greetingService;
        this.timeService = timeService;
    }
```

## Chapter: 4. The Bean Lifecycle
### Lifecycle methods
Sometimes you have behavior, that you need to perform within a class, that requires the dependency injection to be completed, but this behavior either needs to be done before the application is ready for use, or right before it's destroyed. Enter into the picture, Lifecycle Methods.

While Spring is Starting up, the system itself is not usable and this can, occur in the same way during shut down. Spring proxies are not always available during object instantiation, or after destruction has started. This means that not everything is available from a spring perspective, including the framework itself during the construction of an application that you may need behavior-wise in order to perform some task. This will also again apply during the destruction phase as you have no control over the order of garbage collection and you need a way to do some work before Spring goes out of scope.

One thing to note is that these annotations we're going to talk about for these life cycle methods, utilize JSR 250 annotations. These are not Spring specific, so you won't get Spring into your classes when you use these.

* Post Construction is a method that should be executed after construction of the object. Again in the spring world, this allows us to construct the object as part of the IOC lifecycle, which we're going to talk about next, but then do some work using Spring before we hand it off to the actual run time or use phase of the application. The annotation on this method is _@PostConstruct_. 
  * This method has some special cases. It must be a void method. It takes no parameters as the framework itself will run this method. Used, for example, to warming in memory Caches of data, like to make both web service calls as well as database calls to store some data in memory. So by using PostConstruct, I had Spring available, but the application wasn't running, in a state that this Cache would not be pre warmed.
  * This method is technically called, after all property setting is completed. But it's important to know that you can also use Optional Dependencies for your class during this Post construct method.
  * Example: _@PostConstruct public void init_ - it makes sense to call it _init_.

* Pre Destroy is a method that is executed before the class itself is marked for garbage collection. The annotation is _@PreDestroy_ and this is very useful when you need to capture some state before the application closes, and send it to a backing database again, that may be using some form of a Spring abstraction.
  * this is executed technically right when the application context itself closes, so encloses called on that context object. And again, this is well before any garbage collection can take place.
  * _@PreDestroy public void destroy_ - it's guaranteed that this will be called, when the application shuts down, through normal operations before, Spring itself goes out of context. 

### The bean lifecycle
There are three primary phases of the Spring lifecycle:
1) The **initialization phase**, and it by far is the most complex and quite frankly the most interesting of all of the lifecycle elements. While in reality it is one of the shortest phases in chronological time of the three, it is really where we can impact the behavior of our application the most.
2) The **use phase**. The largest majority of time is actually spent in this phase, but most of the interaction with the Spring IOC container here is behind-the-scenes. You may be using abstractions from Spring, but during the use phase most of the work with the framework is through proxies applied during the previous or initialization phase. 
3) The **destruction phase** which of course occurs when the ApplicationContext starts to close by the calling of the close method on it.

#### The initialization phase 
Begins when the ApplicationContext itself is created. This can be done as in our example manually or via runtime like an application server.

It is further broken down into two phases:
1) The setup of the bean factory via its initializations. 
2) The bean initialization itself and instantiation phase where your bean objects are actually created and then managed by the framework.

![Initialization phase](./Initialization%20phase.png)

#### The init phase: Loading bean definitions
The first step of initialization is the loading of the Bean definitions. So in our big picture overview, we're in the box that says Bean Definitions Loaded.

##### Bean Definition Sources

There are several sources of Bean definitions. While we're talking about the lifecycle from a traditional flow, there is some slight variance here on how each of these gets loaded.
* Java config is the most different of the loading of the Bean definitions because the objects are constructed as part of reading the definition. This configuration strategy may impact how these Beans are initialized.
* XML configuration is still valid for configuring the application context. Though it isn't preferred anymore, it is still being used in many applications, especially some older ones. The XML file or files are read to prime the Bean definitions.
* Component scanning and auto configuration. And this is very similar to the way that XML works, except for that instead of loading a file, your classes are scanned and then they actually at that point become available through the Bean definition.


##### Priming the Factory

So when we talk about priming the BeanFactory, we understand what these Bean definitions do.

The first step is that the Bean definitions are loaded into the BeanFactory from all sources and in a running application, you don't just have to choose one. You can have some classes that are component scanned, and some that are configured through XML, and some through Java, and it's just the way that Spring is set up.

So from this point an ID is used to create the index for the factory. And this is the only time anymore that we're going to use ID. It is only used as an index in the factory, everything else will be referenced by type or name.

The BeanFactory only contains references at this point and that's an important point to note. Nothing has been instantiated. It's only references or pointers to the objects with metadata surrounding it about how to configure it.

##### Phase completed

There's a few things that we can say about the lifecycle of our application.
- All Beans have been loaded into the BeanFactory.
- Beans are just references and metadata at this point. There are no objects.
- No objects being instantiated in your code is very important because at this point any manipulation we are going to do will actually be used to instantiate your objects, which is why it's important that the Beans are loaded and just referenced and that there's metadata because we have some processing that we can do in the next step.
