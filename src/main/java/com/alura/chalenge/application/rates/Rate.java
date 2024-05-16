package com.alura.chalenge.application.rates;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.users.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rates")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User student;

    @OneToOne
    @JoinColumn(name = "course_id",referencedColumnName = "id")
    private Course course;

    private Double rate;
}
