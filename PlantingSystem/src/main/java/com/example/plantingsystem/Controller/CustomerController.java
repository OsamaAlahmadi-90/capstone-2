package com.example.plantingsystem.Controller;

import com.example.plantingsystem.Api.ApiResponse;
import com.example.plantingsystem.Model.Customer;
import com.example.plantingsystem.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllCustomers(){
        return ResponseEntity.status(200).body(customerService.getAllCustomers());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCustomer(@RequestBody @Valid Customer customer, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        customerService.addCustomer(customer);
        return ResponseEntity.status(200).body(new ApiResponse("Customer has been added, name: " + customer.getName()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody @Valid Customer customer, @PathVariable Integer id, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }


        if(customerService.updateCustomer(customer, id)){
            return ResponseEntity.status(200).body(new ApiResponse("Customer has been updated with id: " + id));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Could not find customer with id: " + id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id){
        if(customerService.deleteCustomer(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Customer has been deleted with id: " + id));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Could not find customer with id: " + id));
    }

    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<?> getCustomerByName(@PathVariable String name){
        Customer customer = customerService.getCustomerByName(name);

        if(customer == null){
            return ResponseEntity.status(400).body(new ApiResponse("Could not find customer with name: " + name));
        }

        return ResponseEntity.status(200).body(customer);
    }

    @GetMapping("/get-by-phone/{phone}")
    public ResponseEntity<?> getCustomerByPhone(@PathVariable String phone){
        Customer customer = customerService.getCustomerByPhone(phone);

        if(customer == null){
            return ResponseEntity.status(400).body(new ApiResponse("Could not find customer with phone: " + phone));
        }
        return ResponseEntity.status(200).body(customer);
    }

    @GetMapping("/get-before-date/{date}")
    public ResponseEntity<?> getCustomersBeforeDate(@PathVariable LocalDate date){
        List<Customer> customers = customerService.getCustomersBeforeDate(date);

        if(customers.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find customers registered at or before: " + date));
        }
        return ResponseEntity.status(200).body(customers);
    }

    @GetMapping("/get-after-date/{date}")
    public ResponseEntity<?> getCustomersAfterDate(@PathVariable LocalDate date){
        List<Customer> customers = customerService.getCustomersAfterDate(date);

        if(customers.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find customers registered at or after: " + date));
        }

        return ResponseEntity.status(200).body(customers);
    }

    @GetMapping("/plant-care/{plantName}")
    public ResponseEntity<?> getPlantCareAdvice(@PathVariable String plantName) {
        String answer = customerService.aiPlantCareAdvice(plantName);
        return ResponseEntity.status(200).body(answer);
    }


    @GetMapping("/suggest-plants/{text}")
    public ResponseEntity<?> suggestPlantsByLocationAndWeather(@PathVariable String text) {
        String answer = customerService.aiPlantSuggestionsByLocation(text);
        return ResponseEntity.status(200).body(answer);
    }

    @GetMapping("/plant-info/{plantName}")
    public ResponseEntity<?> getPlantInfo(@PathVariable String plantName) {
        String answer = customerService.aiPlantInfo(plantName);
        return ResponseEntity.status(200).body(answer);
    }
}