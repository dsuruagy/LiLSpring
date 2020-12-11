package com.example.university.repo;

import com.example.university.domain.Person;
import com.example.university.domain.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {

    // Simple Query Methods
    Iterable<Student> findByAge(int age);
    Iterable<Student> findByFullTime(boolean fullTime);
    Iterable<Student> findByAttendeeLastName(String lastName);

    // Query Methods with Clauses and Expressions
    Student findByAttendeeFirstNameAndAttendeeLastName(String jane, String doe);
    String findByAttendee(Person person);
    Iterable<Student> findByAgeGreaterThan(int age);
    Iterable<Student> findByAgeLessThan(int age);
    Iterable<Student> findByAttendeeLastNameIgnoreCase(String lastName);
    Iterable<Student> findByAttendeeLastNameLike(String lastName);
    String findFirstByOrderByAttendeeLastNameAsc();
    String findTopByOrderByAgeDesc();
    String findTop3ByOrderByAgeDesc();
}
