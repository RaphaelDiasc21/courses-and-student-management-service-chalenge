package com.alura.chalenge.application.users;

import java.util.Date;

import com.alura.chalenge.application.shared.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String name;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    @Enumerated
    private Role role;

    @Column(name = "creation_date")
    private Date creationDate;
}
