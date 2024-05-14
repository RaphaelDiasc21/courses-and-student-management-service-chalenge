package com.alura.chalenge.application.courses;

import com.alura.chalenge.application.shared.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends CrudRepository<Course,Long>{
    Optional<Course> findByCode(String code);
    Page<Course> findByStatus(Status status, Pageable pageable);
}
