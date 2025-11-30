package com.example.plantingsystem.Controller;

import com.example.plantingsystem.Api.ApiResponse;
import com.example.plantingsystem.Model.Request;
import com.example.plantingsystem.Service.RequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllRequests(){
        return ResponseEntity.status(200).body(requestService.getAllRequests());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRequest(@RequestBody @Valid Request request, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        int result = requestService.addRequest(request);

        if(result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Customer with id: " + request.getCustomerId()));
        }
        if(result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Company with id: " + request.getCompanyId()));
        }
        if(result == 4){
            return ResponseEntity.status(400).body(new ApiResponse("Company with id: " + request.getCompanyId() + " is not approved"));
        }
        if(result == 5){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Plant site with id: " + request.getPlantSiteId()));
        }
        if(result == 6){
            return ResponseEntity.status(400).body(new ApiResponse("Not enough capacity on plant site id: " + request.getPlantSiteId()));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Request has been added with id: " + request.getId()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRequest(@RequestBody @Valid Request request, @PathVariable Integer id,Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        int result = requestService.updateRequest(request, id);

        if(result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Request with id: " + id));
        }
        if(result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Customer with id: " + request.getCustomerId()));
        }
        if(result == 4){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Company with id: " + request.getCompanyId()));
        }
        if(result == 5){
            return ResponseEntity.status(400).body(new ApiResponse("Company with id: " + request.getCompanyId() + " is not approved"));
        }
        if(result == 6){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Plant site with id: " + request.getPlantSiteId()));
        }
        if(result == 7){
            return ResponseEntity.status(400).body(new ApiResponse("Not enough capacity on plant site id: " + request.getPlantSiteId()));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Request has been updated with id: " + id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable Integer id){

        if(requestService.deleteRequest(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Request has been deleted with id: " + id));

        }

        return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Request with id: " + id));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getRequestById(@PathVariable Integer id){
        Request request = requestService.getRequestById(id);
        if(request == null){
            return ResponseEntity.status(400).body(new ApiResponse("Request not found with id: " + id));
        }
        return ResponseEntity.status(200).body(request);
    }


    @GetMapping("/get-by-company/{companyId}")
    public ResponseEntity<?> getRequestsByCompany(@PathVariable Integer companyId){
        List<Request> requests = requestService.getRequestsByCompany(companyId);

        if(requests.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Requests with company id: " + companyId));
        }
        return ResponseEntity.status(200).body(requests);
    }


    @GetMapping("/get-by-customer/{customerId}")
    public ResponseEntity<?> getRequestsByCustomer(@PathVariable Integer customerId){
        List<Request> requests = requestService.getRequestsByCustomer(customerId);

        if(requests.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Requests with customer id: " + customerId));
        }

        return ResponseEntity.status(200).body(requests);
    }


    @GetMapping("/get-max-plants")
    public ResponseEntity<?> getRequestWithMaxNumberOfPlants(){
        Request request = requestService.getRequestWithMaxNumberOfPlants();

        if(request == null){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Request"));
        }

        return ResponseEntity.status(200).body(request);
    }

    @GetMapping("/get-by-plants/{number}")
    public ResponseEntity<?> getRequestsByMinNumberOfPlants(@PathVariable Integer number){
        List<Request> requests = requestService.getRequestsByNumberOfPlants(number);

        if(requests.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Requests with number of plants " + number));
        }
        return ResponseEntity.status(200).body(requests);
    }

    @GetMapping("/get-by-status/{status}")
    public ResponseEntity<?> getRequestsByStatus(@PathVariable String status){
        List<Request> requests = requestService.getRequestsByStatus(status);

        if(requests.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Requests with status: " + status));
        }
        return ResponseEntity.status(200).body(requests);
    }


    @GetMapping("/get-by-requested-at/{dateTime}")
    public ResponseEntity<?> getRequestsByRequestedAt(@PathVariable LocalDateTime dateTime){
        List<Request> requests = requestService.getRequestsByRequestedAt(dateTime);

        if(requests.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Requests requested At: " + dateTime));
        }
        return ResponseEntity.status(200).body(requests);
    }


    @GetMapping("/get-by-scheduled-date/{date}")
    public ResponseEntity<?> getRequestsByScheduledDate(@PathVariable LocalDate date){
        List<Request> requests = requestService.getRequestsByScheduledDate(date);

        if(requests.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Requests scheduled at Date: " + date));
        }
        return ResponseEntity.status(200).body(requests);
    }
}