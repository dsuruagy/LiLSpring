# Creating your first Sprint Boot MicroService
20/10/2020

## Chapter 2
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
        
2.5 Because the main class doesn't have access to the services, because of its static context, we'll 
change the ExplorecalApplication to implement CommandLineRunner.
    Correcting repository code to find by name, instead of code.        