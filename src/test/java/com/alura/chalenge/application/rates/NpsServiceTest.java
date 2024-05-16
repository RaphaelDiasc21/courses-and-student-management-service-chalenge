package com.alura.chalenge.application.rates;

import com.alura.chalenge.application.courses.services.CourseService;
import com.alura.chalenge.application.enrolls.Enroll;
import com.alura.chalenge.application.enrolls.services.EnrollService;
import com.alura.chalenge.application.rates.repositories.RateRepository;
import com.alura.chalenge.application.rates.services.NPSServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class NpsServiceTest {
    @InjectMocks
    NPSServiceImpl npsService;

    @Mock
    CourseService courseService;

    @Mock
    EnrollService enrollService;;

    @Mock
    RateRepository rateRepository;

    @Test
    void given_courseWithFourEnrolls_thenReturnNPSWithExcellent() {
        Rate rate = new Rate();
        rate.setRate(10.0);

        Mockito.when(courseService.findCoursesIds()).thenReturn(List.of(1l));
        Mockito.when(enrollService.findByCourseId(any()))
                .thenReturn(List.of(new Enroll(),new Enroll(),new Enroll(),new Enroll(),new Enroll()));
        Mockito.when(rateRepository.findByCourseId(any())).thenReturn(List.of(rate));

        List<NPS> nps = npsService.getNPSReportPerCourse();
        Assertions.assertEquals(nps.get(0).getNps(),"Excelente");
    }


    @Test
    void given_courseWithFourEnrolls_thenReturnNPSWithAverage() {
        Rate rate = new Rate();
        rate.setRate(10.0);

        Rate rate2 = new Rate();
        rate2.setRate(4.0);

        Mockito.when(courseService.findCoursesIds()).thenReturn(List.of(1l));
        Mockito.when(enrollService.findByCourseId(any()))
                .thenReturn(List.of(new Enroll(),new Enroll(),new Enroll(),new Enroll(),new Enroll()));
        Mockito.when(rateRepository.findByCourseId(any())).thenReturn(List.of(rate,rate2));

        List<NPS> nps = npsService.getNPSReportPerCourse();
        Assertions.assertEquals(nps.get(0).getNps(),"Razoavel");
    }

    @Test
    void given_courseWithFourEnrolls_thenReturnNPSWithVeryGood() {
        Rate rate = new Rate();
        rate.setRate(10.0);

        Rate rate2 = new Rate();
        rate2.setRate(9.0);

        Rate rate3 = new Rate();
        rate3.setRate(5.0);

        Rate rate4 = new Rate();
        rate4.setRate(9.0);

        Rate rate5 = new Rate();
        rate5.setRate(9.0);

        Mockito.when(courseService.findCoursesIds()).thenReturn(List.of(1l));
        Mockito.when(enrollService.findByCourseId(any()))
                .thenReturn(List.of(new Enroll(),new Enroll(),new Enroll(),new Enroll(),new Enroll()));
        Mockito.when(rateRepository.findByCourseId(any())).thenReturn(List.of(rate,rate2,rate3,rate4,rate5));

        List<NPS> nps = npsService.getNPSReportPerCourse();
        Assertions.assertEquals(nps.get(0).getNps(),"Muito bom");
    }


    @Test
    void given_courseWithFourEnrolls_thenReturnNPSWithBad() {
        Rate rate = new Rate();
        rate.setRate(3.0);

        Rate rate2 = new Rate();
        rate2.setRate(9.0);

        Rate rate3 = new Rate();
        rate3.setRate(2.0);

        Rate rate4 = new Rate();
        rate4.setRate(3.0);

        Rate rate5 = new Rate();
        rate5.setRate(1.0);

        Mockito.when(courseService.findCoursesIds()).thenReturn(List.of(1l));
        Mockito.when(enrollService.findByCourseId(any()))
                .thenReturn(List.of(new Enroll(),new Enroll(),new Enroll(),new Enroll(),new Enroll()));
        Mockito.when(rateRepository.findByCourseId(any())).thenReturn(List.of(rate,rate2,rate3,rate4,rate5));

        List<NPS> nps = npsService.getNPSReportPerCourse();
        Assertions.assertEquals(nps.get(0).getNps(),"Ruim");
    }


}
