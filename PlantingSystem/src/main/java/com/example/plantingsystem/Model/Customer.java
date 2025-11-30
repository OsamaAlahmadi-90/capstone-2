package com.example.plantingsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name cannot be empty")
    @Size(min = 5, max = 30,message = "name must be between 4 and 31")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @NotEmpty(message = "username cannot be empty")
    @Size(min = 5, max = 30,message = "username must be between 4 and 31")
    @Column(columnDefinition = "varchar(30) unique not null")
    private String username;

    @NotEmpty(message = "password cannot be empty")
    @Size(min = 3, max = 30,message = "Password must be between 1 and 31")
    @Column(columnDefinition = "varchar(30) not null")
    private String password;

    @Size(max = 20, message = "phone must be less than 20 characters")
    @Column(columnDefinition = "varchar(20)")
    private String phone;

    @NotEmpty(message = "Email cannot be empty")
    @Size(min = 5, max = 30,message = "Email must be between 4 and 31")
    @Email(message = "Email must be valid")
    @Column(columnDefinition = "varchar(30) unique not null")
    private String email;

    @Column(columnDefinition = "date default current_date")
    private LocalDate registrationDate;
}
