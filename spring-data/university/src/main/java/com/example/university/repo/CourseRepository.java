package com.example.university.repo;

import com.example.university.domain.Course;
import com.example.university.view.CourseView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    Iterable<Course> findByDepartmentChairMemberLastName(String lastName);

    @Query("Select c from Course c where c.department.chair.member.lastName=:chair")
    Iterable<Course> findByChairLastName(@Param("chair") String chairLastName);

    Optional<Course> findByName(String name);

    @Query(value = "SELECT * FROM Course c WHERE course_name = ?1", nativeQuery = true)
    Course findByNameSQL(String s);

    @Query("select c from Course c join c.prerequisites p where p.id = ?1")
    Iterable<Course> findCourseByPrerequisite(Integer id);

    @Query("select new com.example.university.view.CourseView" +
            "(c.name, c.instructor.member.lastName, c.department.name) from Course c where c.id = ?1")
    Optional<CourseView> getCourseView(Integer id);

    Iterable<Course> findByCredits(int credit);

    Page<Course> findByCredits(int credit, Pageable pageable);
}
