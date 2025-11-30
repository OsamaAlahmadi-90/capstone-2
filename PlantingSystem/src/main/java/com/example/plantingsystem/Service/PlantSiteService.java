package com.example.plantingsystem.Service;

import com.example.plantingsystem.Model.Customer;
import com.example.plantingsystem.Model.PlantSite;
import com.example.plantingsystem.Repository.CustomerRepository;
import com.example.plantingsystem.Repository.PlantSiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantSiteService {

    private final PlantSiteRepository plantSiteRepository;
    private final CustomerRepository customerRepository;

    public List<PlantSite> getAllPlantSites(){
        return plantSiteRepository.findAll();
    }

    public int addPlantSite(PlantSite plantSite){
        Customer customer = customerRepository.findCustomerById(plantSite.getCustomerId());

        if(customer == null){
            return 2;
        }

        plantSite.setCurrentPlanted(0);
        plantSiteRepository.save(plantSite);
        return 1;

    }

    public int updatePlantSite(PlantSite plantSite, Integer id){

        PlantSite oldSite = plantSiteRepository.findPlantSiteById(id);
        if(oldSite == null){
            return 2;
        }

        Customer customer = customerRepository.findCustomerById(plantSite.getCustomerId());

        if(customer == null){
            return 3;
        }

        oldSite.setName(plantSite.getName());
        oldSite.setLocation(plantSite.getLocation());
        oldSite.setPlantsCapacity(plantSite.getPlantsCapacity());
        oldSite.setCurrentPlanted(plantSite.getCurrentPlanted());

        plantSiteRepository.save(oldSite);
        return 1;
    }

    public boolean deletePlantSite(Integer id){
        PlantSite site = plantSiteRepository.findPlantSiteById(id);

        if(site == null){
            return false;
        }

        plantSiteRepository.delete(site);
        return true;
    }

    public PlantSite getPlantSiteByName(String name){
        return plantSiteRepository.findPlantSiteByName(name);
    }

    public List<PlantSite> getPlantSitesByLocationKeyword(String keyword){
        return plantSiteRepository.findPlantSitesByLocationKeyword(keyword);
    }

    public List<PlantSite> getPlantSitesWithAvailableCapacity(){
        return plantSiteRepository.findPlantSitesWithAvailableCapacity();
    }

    public List<PlantSite> getFullPlantSites(){
        return plantSiteRepository.findFullPlantSites();
    }

    public PlantSite hasAvailableSlots(Integer siteId){
        return plantSiteRepository.findPlantSiteIfHasAvailableCapacity(siteId);
    }
}