package com.example.plantingsystem.Service;

import com.example.plantingsystem.Model.Admin;
import com.example.plantingsystem.Model.Company;
import com.example.plantingsystem.Model.Supplier;
import com.example.plantingsystem.Repository.AdminRepository;
import com.example.plantingsystem.Repository.CompanyRepository;
import com.example.plantingsystem.Repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final CompanyRepository companyRepository;
    private final SupplierRepository supplierRepository;

    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    public void addAdmin(Admin admin){
        admin.setCreatedAt(LocalDateTime.now());
        adminRepository.save(admin);
    }

    public boolean updateAdmin(Admin admin, Integer id){
        Admin oldAdmin = adminRepository.findAdminById(id);
        if(oldAdmin == null){
            return false;
        }

        oldAdmin.setUsername(admin.getUsername());
        oldAdmin.setPassword(admin.getPassword());
        oldAdmin.setEmail(admin.getEmail());
        oldAdmin.setCreatedAt(admin.getCreatedAt());
        adminRepository.save(oldAdmin);
        return true;
    }

    public boolean deleteAdmin(Integer id){
        Admin admin = adminRepository.findAdminById(id);

        if(admin == null){
            return false;
        }

        adminRepository.delete(admin);
        return true;
    }

    public boolean changeCompanyStatus(Integer companyId, String status){
        Company company = companyRepository.findCompanyById(companyId);

        if(company == null){
            return false;
        }

        company.setStatus(status);
        companyRepository.save(company);
        return true;
    }

    public boolean changeSupplierStatus(Integer supplierId, String status){
        Supplier supplier = supplierRepository.findSupplierById(supplierId);

        if(supplier == null){
            return false;
        }

        supplier.setStatus(status);
        supplierRepository.save(supplier);
        return true;
    }

}