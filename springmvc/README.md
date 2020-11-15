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