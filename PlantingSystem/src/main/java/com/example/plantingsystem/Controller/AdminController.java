package com.example.plantingsystem.Controller;

import com.example.plantingsystem.Api.ApiResponse;
import com.example.plantingsystem.Model.Admin;
import com.example.plantingsystem.Model.Company;
import com.example.plantingsystem.Service.AdminService;
import com.example.plantingsystem.Service.CompanyService;
import com.example.plantingsystem.Service.EmailService;
import com.example.plantingsystem.Service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllAdmins(){
        return ResponseEntity.status(200).body(adminService.getAllAdmins());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody @Valid Admin admin, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        adminService.addAdmin(admin);
        return ResponseEntity.status(200).body(new ApiResponse("Admin has been added, username: " + admin.getUsername() + ", and id: "+ admin.getId()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdmin(@RequestBody @Valid Admin admin, @PathVariable Integer id, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }


        if(adminService.updateAdmin(admin, id)){
            return ResponseEntity.status(200).body(new ApiResponse("Admin has been updated with id: " + id));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Admin with id: " + id));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id){

        if(adminService.deleteAdmin(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Admin has been deleted with id: " + id));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Admin with id: " + id));
    }


    @PutMapping("/company-status/{companyId}/{status}")
    public ResponseEntity<?> changeCompanyStatus(@PathVariable Integer companyId, @PathVariable String status){

        if(adminService.changeCompanyStatus(companyId, status)){
            return ResponseEntity.status(200).body(new ApiResponse("Company status has been changed to " + status + ", Company id: " + companyId));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Company with id: " + companyId));
    }

    @PutMapping("/supplier-status/{supplierId}/{status}")
    public ResponseEntity<?> changeSupplierStatus(@PathVariable Integer supplierId, @PathVariable String status){


        if(adminService.changeSupplierStatus(supplierId, status)){
            return ResponseEntity.status(200).body(new ApiResponse("Supplier status has been changed to " + status + ", Supplier id: " + supplierId));
        }

        return ResponseEntity.status(400).body(new ApiResponse("couldn't find Supplier with id: " + supplierId));
    }
}