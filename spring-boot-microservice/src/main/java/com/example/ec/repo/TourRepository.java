package com.example.ec.repo;

import com.example.ec.domain.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface TourRepository extends PagingAndSortingRepository<Tour, String> {
    @Override
    @RestResource(exported = false)
    <S extends Tour> S save(S s);

    @Override
    @RestResource(exported = false)
    <S extends Tour> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    void deleteById(String string);

    @Override
    @RestResource(exported = false)
    void delete(Tour tour);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Tour> iterable);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    Page<Tour> findByTourPackageCode(String code, Pageable pageable);

    /**
     * Only return the main fields of a Tour, not the details
     *
     * @param code tour package code
     * @return tours without details
     */
    @Query(value = "{'tourPackageCode' : ?0 }",
            fields = "{ 'id':1, 'title':1, 'tourPackage':1, 'tourPackageName':1}")
    Page<Tour> findSummaryByTourPackageCode(@Param("code") String code, Pageable pageable);
}
