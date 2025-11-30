package com.example.plantingsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PlantSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull(message = "customerId cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer customerId;

    @NotEmpty(message = "name cannot be empty")
    @Size(min = 5, max = 30,message = "name must be between 5 and 30")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;


    @NotEmpty(message = "Location cannot be empty")
    @Size(min = 5, max = 100,message = "Location must be between 5 and 100")
    @Column(columnDefinition = "varchar(100) not null")
    private String location;


    @NotNull(message = "plants Capacity cannot be null")
    @Min(value = 1, message = "add at least 1 plants capacity")
    @Column(columnDefinition = "int not null")
    private Integer plantsCapacity;

    @NotNull(message = "currentPlanted cannot be null")
    @PositiveOrZero (message = "currentPlanted can't be a negative number")
    @Column(columnDefinition = "int not null")
    private Integer currentPlanted;

}
