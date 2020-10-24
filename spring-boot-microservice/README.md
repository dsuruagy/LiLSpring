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
 Accessing http://localhost:8080/tourPackages/search, the findByName method, implemented on previous steps should be used:
 
    http://localhost:8080/tourPackages/search/findByName?name=Backpack%20Cal
 
 To show it, we should implement a new method on TourRepository to return Tours based on TourPackage code.
 
 ### 3.4 Paging and Sorting
 To implement paging and sorting, it is possible to paginate between the results and use URLs like below. Note the pages metadata and navigation links at the end of the JSON:
 
    http://localhost:8080/tours?size=3&page=0&sort=title
    http://localhost:8080/tours/search/findByTourPackageCode?code=BC&page=1&size=3
    
### 3.5 Controlling API exposure
It is possible to control API exposure, using the following annotations:

    @RepositoryRestResource(exported = false) class annotation
    @RestResource(exported = false) method annotation
    
It can be accomplished on overridden methods from CrudRepository, for example. After that, when we try to use a method not exported, we should receive the status "405 Method Not Allowed".

### 3.6 HAL browser
The API documentation can be viewed on the http://localhost:8080/profile, although it is very ugly. 
To provide better visualization, we could use the HAL browser. HAL comes from JSON Hypertext Application Language and is a simple format that gives a consistent and easy way to hyperlink between resources in our API.

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

    http://localhost:8080/tours/1/ratings?size=2&page=0&sort=score,desc
    http://localhost:8080/tours/1/ratings?size=2&page=0&sort=comment,asc
    http://localhost:8080/tours/1/ratings?size=2&page=2&sort=pk.customerId,asc
    
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