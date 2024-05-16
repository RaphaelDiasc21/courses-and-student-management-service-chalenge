package com.alura.chalenge.application.enrolls;

import java.time.LocalDate;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.users.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "enrolls")
public class Enroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User student;

    @OneToOne
    @JoinColumn(name = "course_id",referencedColumnName = "id")
    private Course course;

    @Column(name = "enroll_date")
    private LocalDate enrollDate = LocalDate.now();
}
