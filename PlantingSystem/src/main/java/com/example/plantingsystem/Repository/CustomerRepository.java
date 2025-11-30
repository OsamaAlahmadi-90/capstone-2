package com.example.plantingsystem.Repository;

import com.example.plantingsystem.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findCustomerById(Integer id);

    Customer findCustomerByName(String name);

    Customer findCustomerByPhone(String phone);

    @Query("select c from Customer c where c.registrationDate <= ?1")
    List<Customer> findCustomersByRegistrationBeforeDate(LocalDate date);

    @Query("select c from Customer c where c.registrationDate >= ?1")
    List<Customer> findCustomersByRegistrationAfterDate(LocalDate date);
}