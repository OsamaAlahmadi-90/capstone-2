package com.example.plantingsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "username cannot be empty")
    @Size(min = 3, max = 30, message = "username must be between 3 and 30")
    @Column(columnDefinition = "varchar(30) unique not null")
    private String username;

    @NotEmpty(message = "password cannot be empty")
    @Size(min = 3, max = 30, message = "password must be between 3 and 30")
    @Column(columnDefinition = "varchar(30) not null")
    private String password;

    @NotEmpty(message = "email cannot be empty")
    @Email(message = "email must be valid")
    @Size(min = 5, max = 50, message = "email must be between 5 and 50")
    @Column(columnDefinition = "varchar(50) unique not null")
    private String email;

    @Column(columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;
}
