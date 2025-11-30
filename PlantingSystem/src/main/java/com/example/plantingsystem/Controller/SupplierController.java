package com.example.plantingsystem.Controller;

import com.example.plantingsystem.Api.ApiResponse;
import com.example.plantingsystem.Model.Supplier;
import com.example.plantingsystem.Service.EmailService;
import com.example.plantingsystem.Service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;
    private final EmailService emailService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllSuppliers(){
        return ResponseEntity.status(200).body(supplierService.getAllSuppliers());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSupplier(@RequestBody @Valid Supplier supplier,Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        emailService.sendEmail(supplier.getEmail(),
                "Company account Created",
                "Your Company account has been created, and it's pending ");
        supplierService.addSupplier(supplier);
        return ResponseEntity.status(200).body(new ApiResponse("Supplier has been added, name: " + supplier.getName() + ", and id: "+ supplier.getId()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSupplier(@RequestBody @Valid Supplier supplier,@PathVariable Integer id, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        if(supplierService.updateSupplier(supplier, id)){
            return ResponseEntity.status(200).body(new ApiResponse("Supplier has been updated with id: " + id));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Supplier with id: " + id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Integer id){

        if(supplierService.deleteSupplier(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Supplier has been deleted with id: " + id));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Supplier with id: " + id));
    }

    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<?> getSupplierByName(@PathVariable String name){
        Supplier supplier = supplierService.getSupplierByName(name);

        if(supplier == null){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Supplier with name: " + name));
        }

        return ResponseEntity.status(200).body(supplier);
    }

    @GetMapping("/get-by-status/{status}")
    public ResponseEntity<?> getSuppliersByStatus(@PathVariable String status){
        List<Supplier> suppliers = supplierService.getSuppliersByStatus(status);

        if(suppliers.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Suppliers with status: " + status));
        }

        return ResponseEntity.status(200).body(suppliers);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveOrder(@PathVariable Integer id){
        int result = supplierService.approveOrder(id);

        if(result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Order with id: " + id));
        }
        if(result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("Order with id: " + id + " is not in pending status"));
        }
        if(result == 4){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Supplier for this order"));
        }
        if(result == 5){
            return ResponseEntity.status(400).body(new ApiResponse("Supplier for this order is not approved"));
        }
        if(result == 6){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Company for this order"));
        }
        if(result == 7){
            return ResponseEntity.status(400).body(new ApiResponse("Company for this order is not approved"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Order has been approved with id: " + id));
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<?> completeOrder(@PathVariable Integer id){
        int result = supplierService.completeOrder(id);

        if(result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Order with id: " + id));
        }
        if(result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("Order with id: " + id + " is not in approved status"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Order has been completed with id: " + id));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectOrder(@PathVariable Integer id){
        int result = supplierService.rejectOrder(id);

        if(result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Order with id: " + id));
        }
        if(result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("orders that are pending or approved can only be cancelled, Id: " + id));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Order has been rejected with id: " + id));
    }
}