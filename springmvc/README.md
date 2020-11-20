# Spring MVC
13/11/2020

## Chapter 1: Getting started and project setup
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
 