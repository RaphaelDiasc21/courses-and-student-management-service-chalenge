package com.alura.chalenge.application.rates.services;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.courses.exceptions.CourseNotFoundExceptionException;
import com.alura.chalenge.application.courses.services.CourseService;
import com.alura.chalenge.application.enrolls.services.EnrollService;
import com.alura.chalenge.application.rates.NPS;
import com.alura.chalenge.application.rates.Rate;
import com.alura.chalenge.application.rates.repositories.RateRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NPSServiceImpl implements NPSService {
    private CourseService courseService;
    private EnrollService enrollService;
    private RateRepository rateRepository;

    public NPSServiceImpl(CourseService courseService,EnrollService enrollService,RateRepository rateRepository) {
        this.courseService = courseService;
        this.enrollService = enrollService;
        this.rateRepository = rateRepository;
    }

    @Override
    public List<NPS> getNPSReportPerCourse() {
        List<NPS> npsList = new ArrayList<>();

        List<Long> coursesIds = courseService.findCoursesIds();

        coursesIds.stream().forEach(courseId -> {
            if(enrollService.findByCourseId(courseId).size() > 4) {
                List<Rate> rates = rateRepository.findByCourseId(courseId);
                float npsScore = calculateNPSScore(rates);
                String npsResult = getNPSResult(npsScore);

                try {
                    Course course = courseService.findById(courseId);
                    npsList.add(new NPS(course,npsScore,npsResult));
                } catch (CourseNotFoundExceptionException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        return npsList;
    }

    private float calculateNPSScore(List<Rate> rates) {
        int promotes = 0;
        int detractors = 0;

        for(Rate rate : rates) {
            if(rate.getRate() >= 9) promotes = promotes + 1;
            if(rate.getRate() < 6) detractors = detractors + 1;
        }

        float promotesAndDetractorsDifference = promotes - detractors;

        if(promotesAndDetractorsDifference == 0) return 0f;

        return (promotesAndDetractorsDifference / rates.size()) * 100;
    }

    private String getNPSResult(float npsScore) {
        if(npsScore >= 75f) return "Excelente";
        if(npsScore >= 50f) return "Muito bom";
        if(npsScore >= 0.0) return "Razoavel";
        return "Ruim";
    }

}
