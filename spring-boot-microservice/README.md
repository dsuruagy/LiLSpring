# Creating your first Sprint Boot MicroService
20/10/2020

## Chapter 2 - Leverage Spring Data JPA Repository Interfaces
2.1 import domain package and classes from exercise file

2.2 implement two repositories: package repo
    TourRepository
    TourPackageRepository
    
2.3 import service package and classes from exercise file

2.4 Add references to repositories and change methods in the services
TourPackageService
        
        createTourPackage(..) - findById orElse save
        lookup() - findAll() 
        total() - count()
        
TourService
        
        createTour(..) - findById(tourPackage) orElseThrow RuntimeException
                        - save a new Tour
        total() - count()
        
2.5 Because the main class doesn't have access to the services, because of its static context, we'll change the ExplorecalApplication to implement CommandLineRunner.
    Correcting repository code to find by name, instead of code. 
    
 ## Chapter 3 - Exposing RESTful APIs with Spring Data REST
 ### 3.1 HATEOAS - Hypermedia As The Engine Of Application State. 
 According to Roy Fielding's, who first published about Rest API specification, an API is not truly RESTful unless it follows a Uniform Interface. One constraint to a Uniform Interface is followed when HATEOAS is employed.
 A RESTful API should not only expose resources endpoints over HTTP. It should also expose API's documentation and automatically provide navigation between resources.
 
 ### 3.2 Creating APIs with Spring Data REST
 No code necessary to implement a REST API, because we included the *spring-boot-starter-data-rest* dependency on our pom.xml:
 
 **To create:**
 HTTP POST /tourPackages {<request body>}
 * On Postman, choose POST, 'raw' body, 'JSON' text with "code" and "name". The response should be '201 created'.
 
 
    TourPackage tp = new TourPackage();
    ...set attributes...
    TourPackageRepository.save(TourPackage tourPackage)
    
 **To read:**
 HTTP GET /tourPackages
 
    TourPackageRepository.findAll()
    
 HTTP GET /tourPackages/<code\>
 
    TourPackageRepository.findById(String code)
 
 **To update:**
 HTTP PUT or PATCH /tourPackages/<code\>{<request body>}
 * On Postman, choose PUT, 'raw' body, 'JSON' text with "name" only. The response should be '200 OK'.
  
  
    TourPackage tp = TourPackageRepository.findById(String code)
    ...set appropriate attributes...
    TourPackageRepository.save(tp)
 
 **To delete:**
 HTTP DELETE /tourPackages/<code\>
 * On Postman, choose DELETE. The response should be '204 No content'.
 
 
    TourPackageRepository.deleteById(String code)
    
 ### 3.3 Explore the /search resource
 Every entity created in Spring Data REST exposes the /search endpoint. 
 Accessing http://localhost:8090/tourPackages/search, the findByName method, implemented on previous steps should be used:
 
    http://localhost:8090/tourPackages/search/findByName?name=Backpack%20Cal
 
 To show it, we should implement a new method on TourRepository to return Tours based on TourPackage code.
 
 ### 3.4 Paging and Sorting
 To implement paging and sorting, it is possible to paginate between the results and use URLs like below. Note the pages metadata and navigation links at the end of the JSON:
 
    http://localhost:8090/tours?size=3&page=0&sort=title
    http://localhost:8090/tours/search/findByTourPackageCode?code=BC&page=1&size=3
    
### 3.5 Controlling API exposure
It is possible to control API exposure, using the following annotations:

    @RepositoryRestResource(exported = false) class annotation
    @RestResource(exported = false) method annotation
    
It can be accomplished on overridden methods from CrudRepository, for example. After that, when we try to use a method not exported, we should receive the status "405 Method Not Allowed".

### 3.6 HAL browser
The API documentation can be viewed on the http://localhost:8090/profile, although it is very ugly. 
To provide better visualization, we could use the HAL browser. HAL comes from JSON Hypertext Application Language and is a simple format that gives a consistent and easy way to hyperlink between resources in our API.

HAL browser is deprecated, so I update it to HAL explorer, as suggested on the startup log.

## Chapter 4 - Expose RESTful APIs with Spring MVC
### 4.1 Choosing the right framework
It is useful to expose API with Spring MVC when we have any of these needs:
* Not using Spring Dara repositories
* API launches an algorithm
* Hide internal data model (entity schema)
* Require business layer service


    @RestController
    @RequestMapping(path = "/examples")
    public class ExampleController{
    ...
    }
    
To **create** a resource Example, we should annotate the method. The @RequestBody maps a request parameter to a Java object:

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Example create(@RequestBody Example example)
    
To **read** use the following annotations:

    // return all
    @GetMapping
    public List<Example> getAllExamples()
    
    // return one
    @GetMapping(path = "/{id}")
    public Example getOneExample(@PathVariable(value = "id") int id)
    
To **update** we should use:

    @PutMapping(path = "/{id}")
    public Example updateAll(@PathVariable(value = "id") int id,
        RequestBody Example example)
        
    @PatchMapping(path = "/{id}")
    public Example updateSome(@PathVariable(value = "id") int id,
            RequestBody Example example)
            
To **delete** use:

    @DeleteMapping(path = "/{id}")
    public Example delete(@PathVariable(value = "id") int id)

### 4.6 Paging and sorting DTOs
Some query examples:

    http://localhost:8090/tours/1/ratings?size=2&page=0&sort=score,desc
    http://localhost:8090/tours/1/ratings?size=2&page=0&sort=comment,asc
    http://localhost:8090/tours/1/ratings?size=2&page=2&sort=pk.customerId,asc
    
## Chapter 5 - Pivot to a MongoDB NoSQL Data Source
### 4.1 Introduction to MongoDB
**New use case**: "... I'd like the micro service to be able to ingest the .json file even if the details differ from tour to tour. Always be present in a tour: 'title' - value of title can change with each import. 'packageType' - value must match tour package name in DB".

**Solution**: No Schema MongoDB

**Advantages**
* Data structure is flexible - easy to add, change, delete
* Entries for an artifact repository can differ

**Disadvantages**

* Does not automatically catch referential integrity errors
* Application responsible for managing data integrity

#### Spring Data Mongo DB

| Spring Data JPA         | Spring Data Mono                                                               |
|-------------------------|--------------------------------------------------------------------------------|
| @Entity                 | @Document                                                                      |
| @Id - javax.persistence | @Id - org.springframerwork.data.annotation                                     |
| @GeneratedValue         | Automatically generated unless provided<br> * Id must be a String if autogenerated |
| @Column                 | N/A                                                                            |
| N/A                     | @Indexed - to increase search performance                                      |
| @ManyToOne              | N/A                                                                            |
| @OneToMany              | N/A                                                                            |
| @ManyToMany             | N/A                                                                            |
| @OneToOne               | N/A                                                                            |
| N/A                     | @DBRef - Refers to another Document within a Document                          |


# Extending, Securing, and Dockerizing Spring Boot Microservices
29/10/2020

## Chapter 1 - Enhancing a Spring Boot Microservice

### 1.1 Identify transactional business services boundaries

The following POST fails because of the existence of one rating for the customerId 4, inserted by the data.sql script. To solve the problem, the TourRatingService.rateMany() method was annotated with @Transactional.

    http://localhost:8090/tours/1/ratings/3?customers=1,2,3,4,5
    
## Chapter 2 - Hardening the Microservice
### 2.4 Documenting APIs with Swagger

    http://localhost:8090/swagger-ui.html

## Chapter 3 - Spring Security with JSON Web Tokens (JWT)
### 3.1 Users and roles

With the addition of UserRepository class, with REST API exposed, we could see the users that are inside the database by accessing http://localhost:8090/users .

By adding spring-boot-stater-security dependency, we're automatically directed to a login screen, because spring-security assumes that all the API must be protected and the users will have sessions, et al.

### 3.2 Spring Security authentication
To test the authentication, do a Post with 

    http://localhost:8090/users/signin

and JSON

    {"username": "admin",
     "password": "letmein"}
     
### 3.4 Understanding JWT - JSON Web Token
#### Spring Security/REST Quandary
##### Spring Security
* Principle and authorities kept on the session

##### Restful API
* Rest is stateless, no session. It means that we have to send the username and password to every restrict API request? No. There are other ways to use the concept of a session with stateless RESTful APIs.

#### API Request Flow> JSON Web Token (JWT)
* Request authentication ==> JWT
* Subsequent requests, will add the token into the header: HTTP header JWT (OAuth is a frequently used implementation that follows this methodology)

#### Header.Payload.Signature
##### Header
* type (json web token)
* hashing algorithm (HMAC SHA256)

##### Payload (also known as claims)
* sub: Subject of the token
* exp: the expiration in NumericDate value
* iat: The time the JWT was issued. Can be used to determine the age of the JWT
* jti: Unique identifier for the JWT. Can be used to prevent the JWT from being replayed
* <app-specific-key>:<app-specific-value>

##### Signature
* Hash value of Header and Payload using a secret string embedded in the applicationpo

### 3.5 Configuring Spring Security for JWT for authorization
At the UserService.signin method, we are returning the authentication token, created on JWTProvider. To decode this token header, payload and signature, we could use the [jsonwebtoken.io website](https://www.jsonwebtoken.io/).

This token should be used on future requests, as a header like this:

    Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3YWxseSIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn0seyJhdXRob3JpdHkiOiJST0xFX0NTUiJ9XSwiaWF0IjoxNjA0OTE0MzEwLCJleHAiOjE2MDQ5MTQ5MTB9.gFmi1bBhqw7h0YRD9cZzNIwn7oyiJ2pw1wwqsd4Q7eU

## Chapter 4 - Leveraging Docker for MySQL Database Access

### 4.2 Running the application with MySQL container

##### Start MySql Container (downloads image if not found)
``
docker run  --detach   --name ec-mysql -p 6604:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=explorecali -e MYSQL_USER=cali_user -e MYSQL_PASSWORD=cali_pass -d mysql
``

##### Interact with Database (link to ec-mysql container) with mysql client
``
docker run -it --link ec-mysql:mysql --rm mysql sh -c 'exec mysql -h"$MYSQL_PORT_3306_TCP_ADDR" -P"$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD"'
``

After starting the application twice, the row numbers on tour table duplicates. So the scripts is good for database initialization, but not for migration.

Stop the docker mysql image to clear the database:

    docker stop ec-mysql
    docker rm ec-mysql
    
## 4.3 Database migration with Flyway

A new table is created to identify the scripts versions that are already executed on the database.

## 4.4 Selecting Spring profiles at runtime

It's a good idea to have more than one profile pointing to different databases. The profile can be changed on the application.properties file, or using a different command line to start, like this:

 Note:The following suggested command didn't work for me: 
    
    mvn spring-boot:run -Dspring.profiles.active=mysql -DskipTests=true
 
 I'm using maven profile to achieve the same result [Activating Spring Boot profile with Maven profile](http://dolszewski.com/spring/spring-boot-properties-per-maven-profile/):
 
    # default profile:
    mvn spring-boot:run -DskipTests=true
    # mysql profile:
    mvn spring-boot:run -Pmysql -DskipTests=true
    
 ## 5.1 Create and run a Java application Docker image
 
 First we make sure the package was generated:
 
    mvn clean package
    
 After that, we modify the _Dockerfile_ as needed and execute the following commands to build and check if it is available:
 
    docker build -t explorecali .
    
    docker images
    
 Run the image (in this case, I'm changing the localport to 8080, but internally the container is using the port defined in application.properties):
 
    docker run --name ec-app -p8080:8090 -d explorecali
    
    docker ps -a
    
To test, I requested the following URL on Postman:
 
    http://localhost:8080/tours
    
## 5.2 Link the Java application and database Docker containers
Stop the container and remove the image created above:

    docker stop ec-app
    docker rmi explorecali -f
    
Build the application and container:

    mvn clean package -Pmysql -DskipTests=true
    docker build -t explorecali .
    
Remove previous running containers...

    docker rm ec-mysql
    docker rm ec-app

...before run them again:
    
    docker run  --detach   --name ec-mysql -p 6604:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=explorecali -e MYSQL_USER=cali_user -e MYSQL_PASSWORD=cali_pass -d mysql
    
    docker run  --name ec-app -p 8080:8090  --link ec-mysql:mysql -d explorecali
    
Check the application log:

    docker logs ec-app
    
## 5.3 Separate application image from database migration
The flyway scripts are bundled within the application. It is better to separate it. Stop and remove the image.

    docker stop ec-app
    docker rm ec-app
    docker rmi explorecali

Build the application with the new profile docker:

    mvn clean package -Pdocker -DskipTests=true
    docker build -t explorecali .

Run the image, passing the parameters. The flyway scripts were copied to my local folder (C:\Users\dsuru\dev\tmp\db\migration) before running this command: 
    
    docker run --name ec-app -p 8080:8090 -v C://Users//dsuru//dev//tmp//db//migration:/var/migration -e server=ec-mysql -e port=3306 -e dbuser=cali_user -e dbpassword=cali_pass --link ec-mysql:mysql -d explorecali
    
## 5.4 Leverage a Docker Maven plugin
There are plugins to automate the image generation.

**Note**: On Windows, check the box 'Expose daemon on tcp://localhost:2375 without TLS', under Docker's General Settings.

With the Spotify plugin, the Dockerfile is not necessary anymore. We can build our images from the build:

    # default:
    mvn package -DskipTests=true docker:build
    # mysql:
    mvn package -Pmysql -DskipTests=true docker:build
    # docker:
    mvn package -Pdocker -DskipTests=true docker:build

Run these images:

    docker run --name ec-app-default -p 8080:8090  -d explorecali-default
    docker run --name ec-app-mysql -p 8181:8090  --link ec-mysql:mysql -d explorecali-mysql
    docker run --name ec-app-docker -p 8282:8090 -v ~/db/migration:/var/migration -e server=ec-mysql -e port=3306 -e dbuser=cali_user -e dbpassword=cali_pass --link ec-mysql:mysql -d explorecali-docker
    
## 5.5 Sharing images with Docker hub
Push a docker image to the repository:

    docker login
    docker tag da3d1cc9e443 dsuruagy/explorecali-default:latest
    docker push dsuruagy/explorecali-default

After removing all the docker images, let's try to run from the repository image:

    docker run --name ec-app-default -p 8080:8090 -d dsuruagy/explorecali-default