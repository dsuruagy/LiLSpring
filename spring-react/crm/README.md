# Spring and React
28/01/2021

## Chapter 1 - Initial Setup
### Initialize a project with Spring Boot
* http://start.spring.io
* package: com.crm
* arctifact: crm
* Dependencies:
    * JPA
    * Rest Repositories
    * H2
    * Lombok

### Initialize the React client project
Inside the crm folder, type this command:

    npx create-react-app client

## Chapter 2 - Spring Models
### Define first model class
* create a model folder inside src/main/java/com/crm
* create a class Contact.java
    * id (long)
    * firstName
    * lastName
    * email

### JPA Repository
* inside model folder, create a new file ContactRepository.java that extends CrudRepository

### Define example data
* inside model folder, create DemoLoader.java that:
    * Implements CommandLineRunner interface
    * Has the above repository autowired
    * Override the run() method to save a new contact into the repository.

## Chapter 2 - HTTP with Spring
### Finalize base server
Insert into application.properties files:

    spring.data.rest.base-path=/api

## Chapter 3 - Basic Frontend with React
### Create contact listing component
* Inside the client folder, run

    npm start

* Edit the App.js file, removing code between divs, and insert an element of Contact component.

* Create the file ./components/contact.js, with the code to render "Hello" and initialize the state with an empty contacts array.

### Add materialize to the project
* On the https://materializecss.com/ website:

    * get the links Get Started > CDN and post into the head of public/index.html
    * components > icons inside the body of index.html.

