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