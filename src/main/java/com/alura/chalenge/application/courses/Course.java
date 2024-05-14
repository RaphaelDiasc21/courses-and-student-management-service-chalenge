package com.alura.chalenge.application.courses;

import java.time.LocalDate;
import java.util.Date;

import com.alura.chalenge.application.shared.enums.Status;
import com.alura.chalenge.application.users.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String code;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    @Enumerated
    private Status status;

    @Column(name = "creation_date")
    private LocalDate creationDate = LocalDate.now();

    @Column(name = "inactive_date")
    private LocalDate inactiveDate;

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private User instructor;
}
