package com.example.university.repo;

import com.example.university.domain.Person;
import com.example.university.domain.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Integer> {

    // Simple Query Methods
    Iterable<Student> findByAge(int age);
    Iterable<Student> findByFullTime(boolean fullTime);
    Iterable<Student> findByAttendeeLastName(String lastName);

    // Query Methods with Clauses and Expressions
    // https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#repository-query-keywords

    // Conditional Expression:
    Student findByAttendeeFirstNameAndAttendeeLastName(String jane, String doe);
    Student findByAttendee(Person person);
    // Expressions with Operators:
    Iterable<Student> findByAgeGreaterThan(int age);
    Iterable<Student> findByAgeLessThan(int age);
    Iterable<Student> findByFullTimeOrAgeLessThan(boolean fullTime, int maxAge);
    Iterable<Student> findByAttendeeLastNameIgnoreCase(String lastName);
    Iterable<Student> findByAttendeeLastNameLike(String lastName); // Wildcard search

    // Expression limiting and Ordering
    // Finds highest student in the alphabet
    Student findFirstByOrderByAttendeeLastNameAsc();
    //Find the oldest student
    Student findTopByOrderByAgeDesc();
    // Find 3 oldest students
    List<Student> findTop3ByOrderByAgeDesc();
}
