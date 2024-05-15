package com.alura.chalenge.application.rates;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.courses.exceptions.CourseNotFoundExceptionException;
import com.alura.chalenge.application.courses.services.CourseService;
import com.alura.chalenge.application.enrolls.services.EnrollService;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;
import com.alura.chalenge.application.shared.notifications.EmailSender;
import com.alura.chalenge.application.users.User;
import com.alura.chalenge.application.users.services.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RateServiceImpl implements RateService {
    final RateRepository rateRepository;
    final CourseService courseService;
    final UserService userService;
    final EnrollService enrollService;

    public RateServiceImpl(RateRepository rateRepository,CourseService courseService, UserService userService,EnrollService enrollService) {
        this.rateRepository = rateRepository;
        this.courseService = courseService;
        this.userService = userService;
        this.enrollService = enrollService;
    }

    @Override
    public Rate create(Rate rate) throws EntityCreationException, EntityNotFoundException {
        Course course = courseService.findById(rate.getCourse().getId());
        User user = userService.findUserByEmailOrUsername(rate.getStudent().getEmail());

        rate.setCourse(course);
        rate.setStudent(user);

        if(rate.getRate() < 6) {
            EmailSender.send(rate.getCourse().getInstructor().getEmail(),
                    "Course with low rate",
                    "Hello %s the course, %s, received a low rate from Student %s"
                            .formatted(
                                    rate.getCourse().getInstructor().getName(),
                                    rate.getCourse().getName(),
                                    rate.getStudent().getName()
                                    ));
        }

        return rateRepository.save(rate);
    }


    @Override
    public List<NPS> getNPSReportPerCourse() {
        List<NPS> npsList = new ArrayList<>();

        List<Long> coursesIds = courseService.findCoursesIds();

        coursesIds.stream().forEach(courseId -> {
            if(enrollService.findByCourseId(courseId).size() > 4) {
                List<Rate> rates = rateRepository.findByCourseId(courseId);
                int npsScore = calculateNPSScore(rates);
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

    private Integer calculateNPSScore(List<Rate> rates) {
        int promotes = 0;
        int detractors = 0;

        for(Rate rate : rates) {
            if(rate.getRate() >= 9) promotes = promotes + 1;
            if(rate.getRate() < 6) detractors = detractors + 1;
        }

       int promotesAndDetractorsDifference = promotes - detractors;

       if(promotesAndDetractorsDifference == 0) return 0;

       return (promotesAndDetractorsDifference / rates.size()) * 100;
    }

    private String getNPSResult(int npsScore) {
        if(npsScore >= 75) return "Excelente";
        if(npsScore >= 50) return "Muito bom";
        if(npsScore >= 0) return "Razoavel";
        return "Ruim";
    }
}
