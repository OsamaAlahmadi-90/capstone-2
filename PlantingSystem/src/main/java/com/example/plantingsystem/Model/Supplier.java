package com.example.plantingsystem.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, max = 50, message = "name must be between 3 and 50")
    @Column(columnDefinition = "varchar(50) unique not null")
    private String name;

    @NotEmpty(message = "username cannot be empty")
    @Size(min = 5, max = 30,message = "username must be between 4 and 31")
    @Column(columnDefinition = "varchar(30) unique not null")
    private String username;

    @NotEmpty(message = "password cannot be empty")
    @Size(min = 3, max = 30,message = "Password must be between 1 and 31")
    @Column(columnDefinition = "varchar(30) not null")
    private String password;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "email must be valid")
    @Size(min = 5, max = 50, message = "email must be between 5 and 50")
    @Column(columnDefinition = "varchar(50) unique not null")
    private String email;

    @Size(max = 20, message = "Phone must be less than 20 characters")
    @Column(columnDefinition = "varchar(20)")
    private String phone;


    @Pattern(regexp = "(?i)^(pending|approved|rejected)$", message = "Status must be pending, approved or rejected")
    @Column(columnDefinition = "varchar(10) not null")
    private String status;


}
