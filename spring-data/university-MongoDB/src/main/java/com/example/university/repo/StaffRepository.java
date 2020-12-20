package com.example.university.repo;

import com.example.university.domain.Staff;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * DataSource Management for the Staff at the University.
 * <p>
 * Created by maryellenbowman.
 */
public interface StaffRepository extends ReactiveMongoRepository<Staff, Integer> {

    Flux<Staff> findByMemberLastName(String lastName);

    @Query("{ 'member.firstName' : ?0 }")
    List<Staff> findByFirstName(String firstName);

}
