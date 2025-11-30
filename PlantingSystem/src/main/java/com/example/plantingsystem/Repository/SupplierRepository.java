package com.example.plantingsystem.Repository;

import com.example.plantingsystem.Model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    Supplier findSupplierById(Integer id);

    Supplier findSupplierByName(String name);

    List<Supplier> findSuppliersByStatus(String status);

}
