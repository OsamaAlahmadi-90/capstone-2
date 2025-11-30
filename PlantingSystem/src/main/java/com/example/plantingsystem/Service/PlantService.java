package com.example.plantingsystem.Service;

import com.example.plantingsystem.Model.Plant;
import com.example.plantingsystem.Model.Supplier;
import com.example.plantingsystem.Repository.PlantRepository;
import com.example.plantingsystem.Repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;
    private final SupplierRepository supplierRepository;

    public List<Plant> getAllPlants(){

        return plantRepository.findAll();
    }

    public int addPlant(Plant plant){
        Supplier supplier = supplierRepository.findSupplierById(plant.getSupplierId());

        if(supplier == null){
            return 2;
        }

        plantRepository.save(plant);
        return 1;
    }


    public int updatePlant(Plant plant, Integer id){
        Plant oldPlant = plantRepository.findPlantById(id);

        if(oldPlant == null){
            return 2;
        }

        Supplier supplier = supplierRepository.findSupplierById(plant.getSupplierId());
        if(supplier == null){
            return 3;
        }


        oldPlant.setName(plant.getName());
        oldPlant.setSpecies(plant.getSpecies());
        oldPlant.setCategory(plant.getCategory());
        oldPlant.setDescription(plant.getDescription());

        plantRepository.save(oldPlant);
        return 1;
    }

    public boolean deletePlant(Integer id){

        Plant plant = plantRepository.findPlantById(id);

        if(plant == null){
            return false;
        }

        plantRepository.delete(plant);
        return true;
    }

    public Plant getPlantByName(String name){
        return plantRepository.findPlantByName(name);
    }

    public List<Plant> getPlantsBySupplierId(Integer supplierId){
        return plantRepository.findPlantsBySupplierId(supplierId);
    }

    public List<Plant> getPlantsBySpeciesKeyword(String keyword){
        return plantRepository.findPlantsBySpeciesKeyword(keyword);
    }

    public Integer getPlantCountBySpecies(String species){
        return plantRepository.countPlantsBySpecies(species);
    }

    public List<Plant> getPlantsByCategory(String category){
        return plantRepository.findPlantsByCategory(category);
    }

}