package com.alura.chalenge.application.rates.services;

import com.alura.chalenge.application.rates.NPS;
import com.alura.chalenge.application.rates.Rate;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;

import java.util.List;

public interface RateService {
    Rate create(Long studentId, String courseCode, Double rateScore) throws EntityCreationException, EntityNotFoundException;
}
