package com.alura.chalenge.application.rates.repositories;

import com.alura.chalenge.application.rates.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateRepository extends JpaRepository<Rate,Long> {
    List<Rate> findByCourseId(Long courseId);
}
