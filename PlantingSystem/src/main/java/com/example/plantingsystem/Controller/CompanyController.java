package com.example.plantingsystem.Controller;

import com.example.plantingsystem.Api.ApiResponse;
import com.example.plantingsystem.Model.Company;
import com.example.plantingsystem.Model.Request;
import com.example.plantingsystem.Service.CompanyService;
import com.example.plantingsystem.Service.EmailService;
import com.example.plantingsystem.Service.RequestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final EmailService emailService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllCompanies(){
        return ResponseEntity.status(200).body(companyService.getAllCompanies());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCompany(@RequestBody @Valid Company company,Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        emailService.sendEmail(company.getEmail(),
                "Company account Created",
                "Your Company account has been created, and it's pending ");
        companyService.addCompany(company);
        return ResponseEntity.status(200).body(new ApiResponse("Company has been added, name: " + company.getName() + ", with id: " + company.getId()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCompany(@RequestBody @Valid Company company,@PathVariable Integer id, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        if(companyService.updateCompany(company, id)){
            return ResponseEntity.status(200).body(new ApiResponse("Company has been updated with id: " + id));
        }

        return ResponseEntity.status(400).body(new ApiResponse("couldn't find Company with id: " + id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Integer id){

        if(companyService.deleteCompany(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Company has been deleted with id: " + id));
        }

        return ResponseEntity.status(400).body(new ApiResponse("couldn't find Company with id: " + id));
    }

    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<?> getCompanyByName(@PathVariable String name){
        Company company = companyService.getCompanyByName(name);


        if(company == null){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Company with name: " + name));
        }

        return ResponseEntity.status(200).body(company);
    }

    @GetMapping("/get-by-service-area/{area}")
    public ResponseEntity<?> getCompaniesByServiceArea(@PathVariable String area){
        List<Company> companies = companyService.getCompaniesByServiceArea(area);

        if(companies.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find companies with service area: " + area));
        }

        return ResponseEntity.status(200).body(companies);
    }

    @GetMapping("/get-by-status/{status}")
    public ResponseEntity<?> getCompaniesByStatus(@PathVariable String status){
        List<Company> companies = companyService.getCompaniesByStatus(status);

        if(companies.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find companies with status: " + status));
        }

        return ResponseEntity.status(200).body(companies);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveRequest(@PathVariable Integer id){
        int result = companyService.approveRequest(id);

        if(result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Request with id: " + id));
        }
        if(result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("Request with id: " + id + " is not in pending status"));
        }
        if(result == 4){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Company for this request"));
        }
        if(result == 5){
            return ResponseEntity.status(400).body(new ApiResponse("Company for this request is not approved"));
        }
        if(result == 6){
            return ResponseEntity.status(400).body(new ApiResponse("Plant site not found for this request"));
        }
        if(result == 7){
            return ResponseEntity.status(400).body(new ApiResponse("Not enough capacity on the plant site for this request"));
        }


        return ResponseEntity.status(200).body(new ApiResponse("Request has been approved with id: " + id));
    }
    @PutMapping("/start/{id}")
    public ResponseEntity<?> startRequest(@PathVariable Integer id){
        int result = companyService.startRequest(id);

        if(result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Request with id: " + id));
        }
        if(result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("Request with id: " + id + " is not in an approved status"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Request has been set to in progress, with id: " + id));
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<?> completeRequest(@PathVariable Integer id){
        int result = companyService.completeRequest(id);

        if(result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't Request with id: " + id));
        }
        if(result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("Request with id: " + id + " is not in in progress status"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Request has been completed with id: " + id));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<?> cancelRequest(@PathVariable Integer id){
        int result = companyService.rejectRequest(id);

        if(result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Request with id: " + id));
        }
        if(result == 4){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find PlantSite related to that request: "));
        }
        if(result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("Only pending, approved or in progress requests can be cancelled. Id: " + id));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Request has been cancelled with id: " + id));
    }

    @GetMapping("/suggest-suppliers/{location}")
    public ResponseEntity<?> suggestSuppliersByLocation(@PathVariable String location) {

        String answer = companyService.aiSupplierSuggestionsByLocation(location);

        return ResponseEntity.status(200).body(answer);
    }
}