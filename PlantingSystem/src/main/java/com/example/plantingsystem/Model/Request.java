package com.example.plantingsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "customer id must not be null")
    @Column(columnDefinition = "int not null")
    private Integer customerId;

    @NotNull(message = "company id must not be null")
    @Column(columnDefinition = "int not null")
    private Integer companyId;

    @NotNull(message = "plant site id must not be null")
    @Column(columnDefinition = "int not null")
    private Integer plantSiteId;

    @Size(max = 255, message = "description must be less than 255 characters")
    @Column(columnDefinition = "varchar(255)")
    private String description;

    @NotNull(message = "number of plants cannot be null")
    @Min(value = 1, message = "number of plants must be at least 1")
    @Column(columnDefinition = "int not null")
    private Integer numberOfPlants;


    @Pattern(regexp = "(?i)^(pending|approved|in_progress|completed|rejected)$", message = "invalid status")
    @Column(columnDefinition = "varchar(15)")
    private String status;


    @Column(columnDefinition = "datetime default current_timestamp")
    private LocalDateTime requestedAt;

    @Column(columnDefinition = "date")
    private LocalDate scheduledDate;

    @Column(columnDefinition = "date")
    private LocalDate completedDate;

}
