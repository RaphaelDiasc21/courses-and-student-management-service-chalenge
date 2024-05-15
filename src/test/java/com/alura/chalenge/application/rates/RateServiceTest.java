package com.alura.chalenge.application.rates;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.enrolls.Enroll;
import com.alura.chalenge.application.enrolls.exceptions.StudentNotEnrolledInTheCourseException;
import com.alura.chalenge.application.enrolls.services.EnrollService;
import com.alura.chalenge.application.rates.repositories.RateRepository;
import com.alura.chalenge.application.rates.services.RateServiceImpl;
import com.alura.chalenge.application.shared.enums.Role;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;
import com.alura.chalenge.application.shared.notifications.EmailSender;
import com.alura.chalenge.application.users.User;
import com.alura.chalenge.application.users.exceptions.UserNotFoundException;
import com.alura.chalenge.application.users.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RateServiceTest {
    @InjectMocks
    RateServiceImpl rateService;

    @Mock
    UserService userService;

    @Mock
    EnrollService enrollService;

    @Mock
    RateRepository rateRepository;

    @Test
    void given_rate_then_createFailedBecauseUserIsNotAStudent() throws UserNotFoundException, StudentNotEnrolledInTheCourseException {
        User user = new User();
        user.setUsername("test");
        user.setRole(Role.INSTRUTOR);


        Mockito.when(userService.findById(any())).thenReturn(user);
        Mockito.when(enrollService.findByStudentIdAndCourseCode(any(),any())).thenReturn(new Enroll());

        Assertions.assertThrows(
                EntityCreationException.class,
                ()-> rateService.create(1l,"teste",9.0),
                "Rating not being doing by a student"
        );
    }


    @Test
    void given_rateWithScoreRateBellowThen6_then_shouldSendNotification() throws UserNotFoundException, StudentNotEnrolledInTheCourseException {
        User user = new User();
        user.setName("test student");
        user.setUsername("test username student");
        user.setRole(Role.ESTUDANTE);

        User instructor = new User();
        instructor.setName("teste instructor");
        instructor.setEmail("teste instructor email");

        Course course = new Course();
        course.setInstructor(instructor);

        Enroll enroll = new Enroll();
        enroll.setCourse(course);
        enroll.setStudent(user);

        Mockito.when(userService.findById(any())).thenReturn(user);
        Mockito.when(enrollService.findByStudentIdAndCourseCode(any(),any())).thenReturn(enroll);

        try(MockedStatic<EmailSender> emailSenderMockedStatic = Mockito.mockStatic(EmailSender.class)) {
            rateService.create(1l,"tes",5.0);
            emailSenderMockedStatic.verify(() -> EmailSender.send(any(),any(),any()),Mockito.times(1));
        } catch (EntityCreationException e) {
            throw new RuntimeException(e);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void given_rateWithScoreRateGreaterThen6_then_shouldSendNotification() throws UserNotFoundException, StudentNotEnrolledInTheCourseException {
        User user = new User();
        user.setName("test student");
        user.setUsername("test username student");
        user.setRole(Role.ESTUDANTE);

        User instructor = new User();
        instructor.setName("teste instructor");
        instructor.setEmail("teste instructor email");

        Course course = new Course();
        course.setInstructor(instructor);

        Enroll enroll = new Enroll();
        enroll.setCourse(course);
        enroll.setStudent(user);

        Mockito.when(userService.findById(any())).thenReturn(user);
        Mockito.when(enrollService.findByStudentIdAndCourseCode(any(),any())).thenReturn(enroll);

        try(MockedStatic<EmailSender> emailSenderMockedStatic = Mockito.mockStatic(EmailSender.class)) {
            rateService.create(1l,"tes",7.0);
            emailSenderMockedStatic.verify(() -> EmailSender.send(any(),any(),any()),Mockito.times(0));
        } catch (EntityCreationException e) {
            throw new RuntimeException(e);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
