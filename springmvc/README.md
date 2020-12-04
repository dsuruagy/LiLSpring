# Spring MVC
13/11/2020

## Chapter 1: Getting started and project setup
### 1.3 Spring MVC Architecture

![Spring MVC Architecture](./Architecture.PNG?raw=true "Spring MVC Architecture")

1. Let's say this is your client, a JSP. It could be any other view technology that you want to work with. 
2. When you have an incoming request from your client it is intercepted by an API called Dispatcher Servlet, and this is the start of the Spring MVC framework. Every request is always uniquely identified by a urlpattern. And this urlpattern is mapped to a filter and a controller dedicated to that request processing. 
3. This URLmapping resolution will be done by Dispatcher Servlet by means of interacting with another special bean type called Handler Mapping. 
4. Once it does this resolution, then it'll delegate the control to the respective controller component. Dispatcher Servlet works on a design pattern called Front Controller. This pattern is common even across the other web MVC frameworks. 
5. All the incoming requests that come in the application are first intercepted by it and bases who wants to go where it'll delegate the control to the respective controller. 
6. The controller is then free to call the rest of the Application Business Logic where it may talk to database or any other third-party service. 
7. Once the logic execution is completed, the flow comes back to the controller. And that's the point where the controller decides the name of the view that the control is supposed to be for, what it do for the display of data. 
8. This logical name is picked up by the Dispatcher Servlet and it consults another component called View resolvers. View resolvers will help Dispatcher Servlet know the mapping of the logical name of the view to the respective JSP template. 
9. Thereafter the Dispatcher Servlet is then going to carry the JSP and display it in the browser as part of the response.


### 1.4 Create MVC Project with Maven
* Create a Maven web app archetype project
* Put in all dependencies for Spring MVC (dependencies attached in the resources folders in exercise files)
* Create SRC and resource folders
* Add static resources (CSS, images, JSP)
* Add the Java configuration to bootstrap _DispatcherServlet_ class

Run application:

    mvn clean package tomcat7:run-war

### 1.8 Generate Spring MVC project with Spring Boot
* Generate a Maven project with Spring Initializr
* Add dependencies to run project with JSPs
* Add JSPs to webapp folder
* Add static resources (CSS, images)
* Map static resoruces in the context configuration class
* Add view resolver in context configuration for JSPs
* Add database configuration in .properties file
* Add _HomeController_ to go to home page

## Chapter 2: Implementing Controller Flows
### 2.1 Basic annotations for controllers
**Controllers in Spring MVC**
* Annotated with @Controller
* Controller specific annotations/objects:
    * @RequestParam - used in methods to receive request params
    * Model/ModelMap - Interface/Implementation that allow us to receive data from the view as key and value pairs. Used as method parameter.
    * ModelAndView - Can hold the name of the view and the data to display on the JSP in one single type.
    * @ModelAttribute - used to annotate parameters for data binding.
    
**Search Use Case Demo**
* Create a SearchController and provide a GetMapping for it
* Insert the search.jsp and add the URL of SearchController to the action of the JSP
* Inside index.jsp, insert the link to the search page 

**@RequestParam and Database code**
* Add @RequestParam and Model parameters on the method
* Map the same request parameter on the form in the search.jsp
* Add Product.java bean

**Access Data from Modelos on JSP**
* Add a _ProductRepository.java_ for database access and a custom method to search results based on the search text
* Modify _SearchController_ to call repository and set it in Model
* Add code to access models in JSP

**Add @ModelAttribute**
* Hplus home -> click on login link -> click on register user link -> Register User
* Add _login.jsp_ and _register.jsp_ to the project
* Add a route in _HomeController_ to go to _login.jsp_ and _register.jsp_ and map them to respective pages.
* create _RegistrationController and add mapping for registering user

**Add Model Attributes in JSP**
* Add a _User.java_ bean to the project
* Add @ModelAttribute to the method signature in _RegistrationController_ and to _register.jsp_
* Add @ModelAttribute in _HomeController_ for default values on _register.jsp_ for new user and genderItems dropdown

**Add Database code**
* Add Spring's form tag library to the project
* Convert HTML form elements to Spring form tags
* Add action mapping to _register.jsp_
* Add _UserRepository_ class
* Modify the _RegistrationController_ to autowire _UserRepository_ and invoke insert method

## Chapter 3: Data validation and binding
**JSR Validations Demo**
* Insert JSR-380 validation annotations in _User.java_ bean
* Enable Controller with @Valid
* Attach _BindingResult_ parameter to method - In the case of validation fail, the errors are populated inside this special object.
* Plug in Spring's form tag on JSP to display error messages
* Localize error messages for validations from .properties file

**Binders**
* @ModelAttribute annotation works with String data types. For example, dateOfBirth is using String data type. If we want to user the Date data type, we must use Binders.
* Binders
    * Extract and bind request data fo Java model objects
    * Required for complex data types defined in models
    * PropertyEditor instances enabled via @Initbinder
    * Custom editors can be implemented too

*Binders Demo*
* Change the data type from String to date

**Converters**
* Transform request data to desired types
* Built in convertors present
* Custom convertors can be plugged in to define specific behavior

*Converters demo*
* Define an enum for gender
* Define a custom converter class which implements Converter interface
* Add conversion logic
* Register the converter in the configuration


## Chapter 4 - Exception Handling
### DispatcherServlet and special bean types
 * _HandlerMapping_ - used to map the incoming request to a specific handler, which is a controller. It may also include pre- and post-processing components like filters (called interceptors in Spring MVC)
 *  _HandlerAdapter_ - helps the DispatcherServlet to invoke the handler, even if it is invoked with XML or annotations. These details are hidden from _DispatcherServlet_.
 * _HandlerExceptionResolver_ - helps with exception handling
 * _ViewResolver_ - helps resolve views
 * _LocaleResolver_ - helps for localization and internationalization
 * _ThemeResolver_ - helps with stylized look and feel
 
 ### Exception Handling in Spring MVC
 * Handled by _DispatcherServlet_
 * Delegates to _HandlerExceptionResolver_ beans
 
 ### Implementations for Exception Handling
 * _ExceptionHandlerExceptionResolver_ - define exception handler methods in controllers. Methods should be dedicated to this and annotaded with _@ExceptionHandler_
 * _SimpleMappingExceptionResolver_ - map each exception class with an error page. Registered inside the context configuration file and offers API to map one exception to a particular error page.
 * _DefaultHandlerExceptionResolver_ - default which maps exceptions to error codes. 
 * _ResponseStatusExceptionResolver_ - resolves custom exceptions using status code define in _@ResponseStatus_
 
 ### Exception Handling Demo in Hplus
 * Login flow work: Hplus home -> Login (find users in the database table)
 * Add "error.jsp" in the JSP folder for /error mapping
 * Disable the default whitelabel error page
 * Create _LoginController.java_ for login, map it to login.jsp
 * Add _@ModelAttribute_ for method argument and the login.jsp
 * Add _@ModelAttribute_ for default login values
 * Add database code for selecting user from DB and autowire to controller
 * Add Spring tags for login page
 * Add a custom exception class in hplus for runtime exceptions
 * User _@ExceptionHandler_ for a method in _LoginController.java_ and simulate exception
 
 ### Global Exception Handling Demo
 * Add a class for exception handler
 * Use _@ControllerAdvice_ on the class level
 
 ## Chapter 5 - Async request processing
 ### Conventional HTTP request processing
 Threads are created by the controller for each request, and stuck up until the business logic finish to process. It is not a feasible thing to do

![image 1](./Async1.PNG?raw=true "Conventional HTTP request processing")

### Async processing at work
* Integrated with Servlet 3.0 specification
* Separate threads for request allocation and blocking calls
* Enable the async processing for Spring MVC
* Let controllers return either of the following
    * _Callable\<T>_
    * _DeferredResult\<T>_
    
![image 2](./Async2.PNG?raw=true "Async processing at work")

### Async processing demo
* Set async processing flag on Dispatcher Servlet
* Add async processing configuration in WebConfig if needed
* Return a _Callable\<String>/DeferredResult\<String>_ from Controllers

## Chapter 6 - View Resolvers
### View Resolvers in Spring MVC
* Resolving views, rendering content, displaying data, all of this is an integral part of any web MVC framework
* Spring provides flexibility with view technologies
* Works with two interfaces
    * _View_ - the actual view, the content that you render in the browser, and the data that you display on it. One view can have information collected and displayed across multiple model attributes.
    * _ViewResolver_ - responsible for actually resolving the views by name, and the view state will never change during the running of the application. Implementations are free to cache views so that they can be loaded faster the nest time they are rendered in the browser.
        * _InternalResourceViewResolver_ 
        * _ResourceBundleViewResolver_ - .properties file used to define names and map to JSPs
        * _XmlViewResolver_ - XML registration of views in form of beans, like other configuration in Spring  
        * _VelocityViewResolver/FreeMarkerViewResolver_
        
### XmlViewResolver Demo
* Configure the _XmlViewResolver_ in application configuration

### ResourceBundleViewResolver Demo
* Configure the _ResourceBundleViewResolver_ in application configuration

### Chaining of View Resolvers
* Application can configure multiple view resolvers
* Set order on each of them using setOrder API
* Higher the order value, the later that view resolver is placed in the chain

## Chapter 7 - Interceptors and Themes
### Introduction to interceptors
* Similar to filters in Servlet API
* Used for pre- and post-proccessing of the request
* Very common use cases for interceptors could be user authentication, session set up or lock some request data.
    * Interceptors generally hold that piece of functionality which is the administrative part of your application. You want to keep that separate from the actual business logic which actually runs in the controller.
    
![Interceptors](./Interceptors.PNG?raw=true "Interceptors")

### Interceptors in Spring MVC
* Implementations of _HandlerInterceptorAdapter_
* Methods:
    * _preHandle_ - before request is processed
    * _postHandle_ - after request is processed
    * _afterCompletion_ - after response is committed
    
### Built-In and Custom Interceptors
* _ThemeChangeInterceptor_
* _LocaleChangeInterceptor_
* Custom interceptors should extend _HandlerInterceptorAdapter_

### Interceptors Demo
![Interceptors Demo](./InterceptorsDemo.PNG?raw=true "Interceptors Demo")
    
* Create a LoggingInterceptor.java class and log request data
* Add the interceptor to registry in the configuration class

### Access Session/Request attributes
* Request/Session attributes could flow across controllers/application
* Stores a model in session using _@SessionAttributes_
* Access session data using _@SessionAttribute_
* Access any request data using _@RequestAttribute_

### @SessionAttributes Demo
* Set up _@SessionAttributes_ for login form, storing the login model in the session. Make sure that we have a _@ModelAttribute_ with the same name as the _@SessionAttributes_. In our application, this model was defined in a _@ControllerAdvice_ in _DefaultModelAttributeController.java_.
* Create _UserProfileController.java_, for access the model stored in session
* Retrieve the session attributes in _UserProfileController.java_
* Forward to _UserProfileController.java_ controller from _LoginController.java_

### Ending User Session
* Create a _LogoutController.java_
* Invalidate session

### Themes in Spring MVC
* Static resources - CSS, images
* Why is a theme required?
* Define theme using _org.springframework.ui.context.ThemeSource_
* Default implementation is _ResourceBundleThemeSource_
* ThemeResolver facilitates resolution

### Spring MVC Resolving Themes Demo
* Define two separate themes (CSS resources)
* Define respective properties files for those themes
* Use one of ThemeResolver to decide which theme to use:
    * _CookieThemeResolver_
    * _SessionThemeResolver_
    * _FixedThemeResolver_
    
* Configure _ThemeChangeInterceptor_ to tap a change in theme value for every request
* Use <spring:theme/> to refer themed keys from .properties file on the JSP
* Change the theme. For example, access this link: http://localhost:8090/home?theme=client-theme2