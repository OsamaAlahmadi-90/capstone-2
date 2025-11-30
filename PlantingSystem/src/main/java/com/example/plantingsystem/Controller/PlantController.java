package com.example.plantingsystem.Controller;

import com.example.plantingsystem.Api.ApiResponse;
import com.example.plantingsystem.Model.Plant;
import com.example.plantingsystem.Service.PlantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plant")
@RequiredArgsConstructor
public class PlantController {
    private final PlantService plantService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllPlants(){
        return ResponseEntity.status(200).body(plantService.getAllPlants());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPlant(@RequestBody @Valid Plant plant,Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        int result = plantService.addPlant(plant);

        if(result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Supplier with id: " + plant.getSupplierId()));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Plant has been added, name: " + plant.getName()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePlant(@RequestBody @Valid Plant plant,@PathVariable Integer id, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        int result = plantService.updatePlant(plant, id);

        if(result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Plant with id: " + id));
        }
        if(result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Supplier with id: " + plant.getSupplierId()));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Plant has been updated with id: " + id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePlant(@PathVariable Integer id) {

        if (plantService.deletePlant(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Plant has been deleted with id: " + id));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Plant with id: " + id));

    }

    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<?> getPlantByName(@PathVariable String name){
        Plant plant = plantService.getPlantByName(name);

        if(plant == null){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Plant with name: " + name));
        }
        return ResponseEntity.status(200).body(plant);
    }

    @GetMapping("/get-by-supplier/{supplierId}")
    public ResponseEntity<?> getPlantsBySupplier(@PathVariable Integer supplierId){
        List<Plant> plants = plantService.getPlantsBySupplierId(supplierId);

        if(plants.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find plants supplier id: " + supplierId));
        }
        return ResponseEntity.status(200).body(plants);
    }

    @GetMapping("/get-by-species-keyword/{keyword}")
    public ResponseEntity<?> getPlantsBySpeciesKeyword(@PathVariable String keyword){
        List<Plant> plants = plantService.getPlantsBySpeciesKeyword(keyword);

        if(plants.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find plants with species containing: " + keyword));
        }

        return ResponseEntity.status(200).body(plants);
    }

    @GetMapping("/count-by-species/{species}")
    public ResponseEntity<?> getPlantCountBySpecies(@PathVariable String species){
        Integer count = plantService.getPlantCountBySpecies(species);

        return ResponseEntity.status(200).body(new ApiResponse("Number of plants with species '" + species + "' is: " + count));
    }

    @GetMapping("/get-by-category/{category}")
    public ResponseEntity<?> getPlantsByCategory(@PathVariable String category){
        List<Plant> plants = plantService.getPlantsByCategory(category);

        if(plants.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find plants with category: " + category));
        }

        return ResponseEntity.status(200).body(plants);
    }
}