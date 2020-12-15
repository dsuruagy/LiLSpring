package com.example.university.repo;

import com.example.university.domain.Staff;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StaffRepository extends PagingAndSortingRepository<Staff, Integer> {
}
