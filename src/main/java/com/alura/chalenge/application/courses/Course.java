package com.alura.chalenge.application.courses;

import java.util.Date;

import com.alura.chalenge.application.shared.enums.Status;
import com.alura.chalenge.application.users.User;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

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
    private Date creationDate;

    @Column(name = "inactive_date")
    private Date inactiveDate;

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private User instructor;
}
