package com.example.plantingsystem.Service;

import com.example.plantingsystem.Model.Company;
import com.example.plantingsystem.Model.Customer;
import com.example.plantingsystem.Model.PlantSite;
import com.example.plantingsystem.Model.Request;
import com.example.plantingsystem.Repository.CompanyRepository;
import com.example.plantingsystem.Repository.CustomerRepository;
import com.example.plantingsystem.Repository.PlantSiteRepository;
import com.example.plantingsystem.Repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final CustomerRepository customerRepository;
    private final PlantSiteRepository plantSiteRepository;
    private final CompanyRepository companyRepository;

    public List<Request> getAllRequests(){
        return requestRepository.findAll();
    }


    public int addRequest(Request request){
        Customer customer = customerRepository.findCustomerById(request.getCustomerId());

        if(customer == null){
            return 2;
        }

        Company company = companyRepository.findCompanyById(request.getCompanyId());
        if(company == null){
            return 3;
        }

        if(!company.getStatus().equalsIgnoreCase("approved")){
            return 4;
        }

        PlantSite site = plantSiteRepository.findPlantSiteById(request.getPlantSiteId());
        if(site == null){
            return 5;
        }

        int available = site.getPlantsCapacity() - site.getCurrentPlanted();
        if(request.getNumberOfPlants() > available){
            return 6;
        }


        request.setRequestedAt(LocalDateTime.now());
        request.setStatus("pending");
        requestRepository.save(request);
        return 1;
    }


    public int updateRequest(Request request, Integer id){
        Request oldRequest = requestRepository.findRequestById(id);

        if(oldRequest == null){
            return 2;
        }

        Customer customer = customerRepository.findCustomerById(request.getCustomerId());
        if(customer == null){
            return 3;
        }

        Company company = companyRepository.findCompanyById(request.getCompanyId());

        if(company == null){
            return 4;
        }

        if(!company.getStatus().equalsIgnoreCase("approved")){
            return 5;
        }

        PlantSite plantSite = plantSiteRepository.findPlantSiteById(request.getPlantSiteId());
        if(plantSite == null){
            return 6;
        }

        int availableSpace = plantSite.getPlantsCapacity() - plantSite.getCurrentPlanted();
        if(request.getNumberOfPlants() > availableSpace){
            return 7;
        }


        oldRequest.setDescription(request.getDescription());
        oldRequest.setNumberOfPlants(request.getNumberOfPlants());
        oldRequest.setRequestedAt(request.getRequestedAt());
        oldRequest.setScheduledDate(request.getScheduledDate());
        requestRepository.save(oldRequest);
        return 1;
    }

    public boolean deleteRequest(Integer id){
        Request request = requestRepository.findRequestById(id);

        if(request == null){
            return false;
        }

        requestRepository.delete(request);
        return true;
    }

    public Request getRequestById(Integer id){
        return requestRepository.findRequestById(id);
    }

    public List<Request> getRequestsByCompany(Integer companyId){
        return requestRepository.findRequestsByCompanyId(companyId);
    }

    public List<Request> getRequestsByCustomer(Integer customerId){
        return requestRepository.findRequestsByCustomerId(customerId);
    }


    public Request getRequestWithMaxNumberOfPlants(){
        return requestRepository.findMaxNumberOfPlants();
    }

    public List<Request> getRequestsByNumberOfPlants(Integer number){
        return requestRepository.findRequestsByNumberOfPlants(number);
    }

    public List<Request> getRequestsByStatus(String status){
        return requestRepository.findRequestsByStatus(status);
    }

    public List<Request> getRequestsByRequestedAt(LocalDateTime requestedAt){
        return requestRepository.findRequestsByRequestedAt(requestedAt);
    }

    public List<Request> getRequestsByScheduledDate(LocalDate date){
        return requestRepository.findRequestsByScheduledDate(date);
    }
}