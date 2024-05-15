package com.alura.chalenge.application.enrolls;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollRepository extends CrudRepository<Enroll,Long> {
    @Query("SELECT e FROM Enroll AS e WHERE e.course.id = :courseId AND e.student.email = :studentEmail")
    Optional<Enroll> findByStudentAndCourse(Long courseId,String studentEmail);


    List<Enroll> findByCourse_Id(Long courseId);
}
