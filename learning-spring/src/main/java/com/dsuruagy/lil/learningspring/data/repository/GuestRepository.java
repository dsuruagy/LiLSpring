package com.dsuruagy.lil.learningspring.data.repository;

import com.dsuruagy.lil.learningspring.data.entity.Guest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends CrudRepository<Guest, Long>{
    
}
