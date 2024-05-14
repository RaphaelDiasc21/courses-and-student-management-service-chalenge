package com.alura.chalenge.application.enrolls;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EnrollRepository extends CrudRepository<Enroll,Long> {
    @Query("SELECT e FROM Enroll AS e WHERE e.course.id = :courseId AND e.student.id = :studentId")
    Optional<Enroll> findByStudentAndCourse(Long courseId,Long studentId);
}
