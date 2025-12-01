package com.example.plantingsystem.Service;

import com.example.plantingsystem.Model.Company;
import com.example.plantingsystem.Model.PlantSite;
import com.example.plantingsystem.Model.Request;
import com.example.plantingsystem.Repository.CompanyRepository;
import com.example.plantingsystem.Repository.CustomerRepository;
import com.example.plantingsystem.Repository.PlantSiteRepository;
import com.example.plantingsystem.Repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final RequestRepository requestRepository;
    private final PlantSiteRepository plantSiteRepository;
    private final PlantAiService plantAiService;

    public List<Company> getAllCompanies(){

        return companyRepository.findAll();

    }

    public void addCompany(Company company){

        company.setStatus("pending");
        companyRepository.save(company);

    }

    public boolean updateCompany(Company company, Integer id){
        Company oldCompany = companyRepository.findCompanyById(id);
        if(oldCompany == null){
            return false;
        }


        oldCompany.setName(company.getName());
        oldCompany.setEmail(company.getEmail());
        oldCompany.setPhone(company.getPhone());
        oldCompany.setServiceArea(company.getServiceArea());
        oldCompany.setStatus(company.getStatus());

        companyRepository.save(oldCompany);
        return true;
    }

    public boolean deleteCompany(Integer id){
        Company company = companyRepository.findCompanyById(id);


        if(company == null){
            return false;
        }

        companyRepository.delete(company);
        return true;
    }

    public Company getCompanyById(Integer id){
        return companyRepository.findCompanyById(id);
    }

    public Company getCompanyByName(String name){

        return companyRepository.findCompanyByName(name);
    }

    public List<Company> getCompaniesByServiceArea(String area){

        return companyRepository.findCompaniesByServiceArea(area);
    }

    public List<Company> getCompaniesByStatus(String status){

        return companyRepository.findCompaniesByStatus(status);
    }


    public int approveRequest(Integer requestId){
        Request request = requestRepository.findRequestById(requestId);
        if(request == null){
            return 2;
        }

        if(!request.getStatus().equalsIgnoreCase("pending")){
            return 3;
        }

        Company company = companyRepository.findCompanyById(request.getCompanyId());
        if(company == null){
            return 4;
        }

        if(!company.getStatus().equalsIgnoreCase("approved")){
            return 5;
        }

        PlantSite site = plantSiteRepository.findPlantSiteById(request.getPlantSiteId());
        if(site == null){
            return 6;
        }

        int availableSpace = site.getPlantsCapacity() - site.getCurrentPlanted();
        if(request.getNumberOfPlants() > availableSpace){
            return 7;
        }

        request.setStatus("approved");
        requestRepository.save(request);

        site.setCurrentPlanted(site.getCurrentPlanted() + request.getNumberOfPlants());
        plantSiteRepository.save(site);
        return 1;
    }


    public int startRequest(Integer requestId){
        Request request = requestRepository.findRequestById(requestId);
        if(request == null){
            return 2;
        }

        if(!request.getStatus().equalsIgnoreCase("approved")){
            return 3;
        }

        request.setStatus("in_progress");
        requestRepository.save(request);
        return 1;
    }


    public int completeRequest(Integer requestId){
        Request request = requestRepository.findRequestById(requestId);
        if(request == null){
            return 2;
        }

        if(!request.getStatus().equalsIgnoreCase("in_progress")){
            return 3;
        }

        request.setStatus("completed");
        request.setCompletedDate(LocalDate.now());
        requestRepository.save(request);
        return 1;
    }

    public int rejectRequest(Integer requestId){

        Request request = requestRepository.findRequestById(requestId);
        if(request == null){
            return 2;
        }

        PlantSite site = plantSiteRepository.findPlantSiteById(request.getPlantSiteId());
        if(site == null){
            return 4;
        }

        String status = request.getStatus().toLowerCase();

        if(!status.equals("pending")){
            return 3;
        }


        request.setStatus("rejected");
        requestRepository.save(request);
        return 1;
    }

    public String aiSupplierSuggestionsByLocation(String location) {
        return plantAiService.getSupplierSuggestionsByLocation(location);
    }


}
