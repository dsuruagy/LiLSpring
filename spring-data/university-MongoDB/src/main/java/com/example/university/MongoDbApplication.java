package com.example.university;

import com.example.university.domain.Department;
import com.example.university.domain.Person;
import com.example.university.domain.Staff;
import com.example.university.repo.DepartmentRepository;
import com.example.university.repo.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

/**
 * Main Spring Boot Class for the University Application.
 * On Startup Initialize Database with Staff and Departments.
 * <p>
 * Created by maryellenbowman
 */
@SpringBootApplication
public class MongoDbApplication implements CommandLineRunner {
    @Autowired
    StaffRepository staffRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public static void main(String[] args) {
        SpringApplication.run(MongoDbApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        //Staff
        /*Mono<Staff> deanJones = staffRepository.save(new Staff(1, new Person("John", "Jones")));
        Mono<Staff> deanMartin = staffRepository.save(new Staff(2, new Person("Matthew", "Martin")));
        Mono<Staff> profBrown = staffRepository.save(new Staff(3, new Person("James", "Brown")));
        Mono<Staff> profMiller = staffRepository.save(new Staff(4, new Person("Judy", "Miller")));
        Mono<Staff> profDavis = staffRepository.save(new Staff(5, new Person("James", "Davis")));
        Mono<Staff> profMoore = staffRepository.save(new Staff(6, new Person("Allison", "Moore")));
        Mono<Staff> profThomas = staffRepository.save(new Staff(7, new Person("Tom", "Thomas")));
        Mono<Staff> profGreen = staffRepository.save(new Staff(8, new Person("Graham", "Green")));
        Mono<Staff> profWhite = staffRepository.save(new Staff(9, new Person("Whitney", "White")));
        Mono<Staff> profBlack = staffRepository.save(new Staff(10, new Person("Jack", "Black")));
        Mono<Staff> profKing = staffRepository.save(new Staff(11, new Person("Queen", "King")));

        //Departments
        Mono<Department> humanities = departmentRepository.save(new Department(100, "Humanities", deanJones.block()));
        Mono<Department> naturalSciences = departmentRepository.save(new Department(200, "Natural Sciences", deanMartin.block()));
        Mono<Department> socialSciences = departmentRepository.save(new Department(300, "Social Sciences", deanJones.block()));*/

    }
}
