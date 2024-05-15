package com.alura.chalenge.application.enrolls.services;

import com.alura.chalenge.application.enrolls.Enroll;
import com.alura.chalenge.application.shared.interfaces.EntityService;

import java.util.List;

public interface EnrollService extends EntityService<Enroll> {
    List<Enroll> findByCourseId(Long courseId);
}
