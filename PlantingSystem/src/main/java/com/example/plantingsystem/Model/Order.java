package com.example.plantingsystem.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "company id cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer companyId;

    @NotNull(message = "supplier id cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer supplierId;

    @NotNull(message = "plant id cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer plantId;

    @NotNull(message = "quantity cannot be null")
    @Min(value = 1, message = "quantity must be at least 1")
    @Column(columnDefinition = "int not null")
    private Integer quantity;

    @Column(columnDefinition = "datetime default current_timestamp")
    private LocalDateTime orderDate;


    @Pattern(regexp = "(?i)^(pending|approved|completed|rejected)$", message = "status must be either pending, confirmed, delivered, or cancelled)")
    @Column(columnDefinition = "varchar(10)")
    private String status;

}
