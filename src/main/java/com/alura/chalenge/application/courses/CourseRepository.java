package com.alura.chalenge.application.courses;

import com.alura.chalenge.application.shared.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long>, JpaSpecificationExecutor<Course> {
    Optional<Course> findByCode(String code);
    Page<Course> findByStatus(Status status, Pageable pageable);
}
