package com.example.plantingsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "supplier id must not be null")
    @Column(columnDefinition = "int not null")
    private Integer supplierId;

    @NotEmpty(message = "plant name cannot be empty")
    @Size(min = 3, max = 50, message = "plant name must be between 3 and 50")
    @Column(columnDefinition = "varchar(50) not null")
    private String name;

    @NotEmpty(message = "species cannot be empty")
    @Size(min = 3, max = 50, message = "species must be between 3 and 50")
    @Column(columnDefinition = "varchar(50) not null")
    private String species;

    @Size(max = 30, message = "category must be less than 30 characters")
    @Column(columnDefinition = "varchar(30)")
    private String category;

    @Size(max = 255, message = "description must be less than 255 characters")
    @Column(columnDefinition = "varchar(255)")
    private String description;
}
