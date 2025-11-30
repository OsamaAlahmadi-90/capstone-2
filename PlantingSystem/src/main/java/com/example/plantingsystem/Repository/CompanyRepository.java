package com.example.plantingsystem.Repository;

import com.example.plantingsystem.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Company findCompanyById(Integer id);

    Company findCompanyByName(String name);

    List<Company> findCompaniesByServiceArea(String serviceArea);

    List<Company> findCompaniesByStatus(String status);

}
