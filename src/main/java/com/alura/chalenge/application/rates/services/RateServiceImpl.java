package com.alura.chalenge.application.rates.services;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.courses.exceptions.CourseNotFoundExceptionException;
import com.alura.chalenge.application.courses.services.CourseService;
import com.alura.chalenge.application.enrolls.Enroll;
import com.alura.chalenge.application.enrolls.exceptions.StudentNotEnrolledInTheCourseException;
import com.alura.chalenge.application.enrolls.services.EnrollService;
import com.alura.chalenge.application.rates.NPS;
import com.alura.chalenge.application.rates.Rate;
import com.alura.chalenge.application.rates.exceptions.RatingNotDoingByStudentException;
import com.alura.chalenge.application.rates.repositories.RateRepository;
import com.alura.chalenge.application.shared.enums.Role;
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
    final UserService userService;
    final EnrollService enrollService;

    public RateServiceImpl(RateRepository rateRepository, UserService userService,EnrollService enrollService) {
        this.rateRepository = rateRepository;
        this.userService = userService;
        this.enrollService = enrollService;
    }

    @Override
    public Rate create(Long studentId,String courseCode, Double rateScore) throws EntityCreationException, EntityNotFoundException {
       try {
           User user = userService.findById(studentId);
           Enroll enroll = enrollService.findByStudentIdAndCourseCode(studentId,courseCode);

           isUserNotAStudent(user);
           Rate rate = new Rate();
           rate.setCourse(enroll.getCourse());
           rate.setStudent(user);
           rate.setRate(rateScore);

           if(rate.getRate() < 6) {
               sendNotificationToInsctructorBecauseOfLowRate(rate);
           }

           return rateRepository.save(rate);
       }catch (StudentNotEnrolledInTheCourseException | RatingNotDoingByStudentException exception) {
           throw new EntityCreationException(exception.getMessage());
       }

    }

    private void isUserNotAStudent(User user) throws RatingNotDoingByStudentException {
        if(!user.getRole().equals(Role.ESTUDANTE)) {
            throw new RatingNotDoingByStudentException();
        }
    }

    private void sendNotificationToInsctructorBecauseOfLowRate(Rate rate) {
        EmailSender.send(rate.getCourse().getInstructor().getEmail(),
                "Course with low rate",
                "Hello %s the course, %s, received a low rate from Student %s"
                        .formatted(
                                rate.getCourse().getInstructor().getName(),
                                rate.getCourse().getName(),
                                rate.getStudent().getName()
                        ));
    }
}
