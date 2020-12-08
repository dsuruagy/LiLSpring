# Spring Data 2
07/12/2020

## Chapter 1 - Spring Data Commons
### Mission
* Legacy of providing superior data access frameworks
* Vast collection of enterprise solutions

### Spring Data Commons
https://projects.spring.io/spring-data
1. Java business entities <-> Persistent target datastore records
2. Lookup records
3. Update records
4. Delete records

#### Repository Pattern
* Based on Repository Pattern
* Create, read, update and delete (CRUDRepository)
* JpaRepository
* MongoRepository
* GemfireRepository

## Chapter 2 - Understanding JPA for Object-Relational Mapping
### Object-Relational Mapping (ORM)
* Physical model to the Logical model
* Physical model = Relational database
* Logical model = Java domain objects

### ORM with Standard Java
1. Open a transaction
2. Make a SQL Query
3. Iterate through each records
4. Iterate through each fiels in a records
5. Extract field, respecting data type
6. Map to the Java object/attribute
7. Close the transactions
7a. For Insert/Update query - commit/rollback transaction

### JPA Is Just a Spectification
* Implementation frameworks: Hibernate, TopLink, and Java EE application servers
* Metadata mapping (XML or Java annotations)
	Java entities <===> Tables
	Java attributes <===> Column/fields
* EntityManager
	Create, read, update, and delete entities
* Still requires boilerplate code to make it work
	
### Map Multiple Tables to Java Classes
* Enrollment is a join table

### Java Persistence Query Language (JPQL)
* Interact with entities and their persistent state
* Portable to any database management system
* Syntax similar to SQL
	* JPQL - entites and attributes
	* SQL - tables and columns
	
### JPA Examples
* Create

	@PersistenceContext
	EntityManager entityManager;
	
	Student create(String name, boolean isFullTime, int age) {
		entityManager.getTransaction().begin();
		Student newStudent = new Student(name, isFullTime, age);
		entityManager.persist(newStudent);
		entityManager.getTransaction().commit();
		entityManger.close();
		return newStudent;
	}
	
* Update

	@PersistenceContext
	EntityManager entityManager;
	
	void updateAge(int studentId, int age) {
		entityManager.getTransaction().begin();
		Student student = entityManager.find(Student.class, studentId);
		entityManager.persist(student);
		entityManager.getTransaction().commit();
		entityManger.close();
	}
	
* Read/Lookup

* Update

	@PersistenceContext
	EntityManager entityManager;
	
	List<Student> read(String nameLike) {
		Query query = entityManager.createQuery(
		"select s from Student s where s.name LIKE :someName", Student.class);
		query.setParameter("someName", "%" + nameLike + "%");
		List<Student> result = query.getResultList();
		entityManger.close();
		return result;
	}
	
## Chapter 3 - Introduction to Spring Data JPA
### Spring Data Repository Interfaces

	public interface Repository<T, ID>
	
	T	Domain type the repository manages
	ID	Type of the entity id
	
	
	public interface CrudRepository<T, ID> extends Repository<T, ID>
	
### Create/Update Methods

	T save(T entity);
	
	Iterable<T> saveAll(Iterable<T> entity);
	
### Delete Methods
* Spring Data v2

	void deleteById(ID id)
	void deleteAll(Iterable<? extends T>)
	void delete(T var1)
	void deleteAll()
	
* Spring Data v1.x
	void delete(ID id)
	
### Read Methods
* Spring Data v2

	Optional<T> findById(ID id)
	Iterable<T> findAllById(Iterable<ID> ids)
	Iterable<T> findAll()
	long count()
	boolean existsById(ID id)
	
* Spring Data v1.x
	
	T findOne(ID id)
	Iterable<T> findAll(Iterable<ID> ids)