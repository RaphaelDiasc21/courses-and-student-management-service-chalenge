package com.alura.chalenge.application.rates;

import com.alura.chalenge.application.shared.interfaces.EntityService;

import java.util.List;

public interface RateService extends EntityService<Rate> {
    List<NPS> getNPSReportPerCourse();
}
