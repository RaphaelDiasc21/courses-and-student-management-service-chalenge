package com.alura.chalenge.application.enrolls;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollRepository extends CrudRepository<Enroll,Long> {
    @Query("SELECT e FROM Enroll AS e WHERE e.course.code = :courseCode AND (e.student.email = :studentIdentification OR e.student.username = :studentIdentification)")
    Optional<Enroll> findByStudentAndCourse(String courseCode, String studentIdentification);

    Optional<Enroll> findByStudentIdAndCourseCode(Long studentId,String courseCode);

    List<Enroll> findByCourse_Id(Long courseId);
}
