package com.example.plantingsystem.Controller;

import com.example.plantingsystem.Api.ApiResponse;
import com.example.plantingsystem.Model.PlantSite;
import com.example.plantingsystem.Service.PlantSiteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plant-site")
@RequiredArgsConstructor
public class PlantSiteController {

    private final PlantSiteService plantSiteService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllPlantSites(){
        return ResponseEntity.status(200).body(plantSiteService.getAllPlantSites());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPlantSite(@RequestBody @Valid PlantSite plantSite,Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        int result = plantSiteService.addPlantSite(plantSite);

        if(result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Customer with id: " + plantSite.getCustomerId()));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Plant site has been added, name: " + plantSite.getName() + ", and id: " + plantSite.getId()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePlantSite(@RequestBody @Valid PlantSite plantSite,@PathVariable Integer id,Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        int result = plantSiteService.updatePlantSite(plantSite, id);

        if(result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Plant site not found with id: " + id));
        }

        if(result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("Could not find Customer with id: " + plantSite.getCustomerId()));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Plant site has been updated with id: " + id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePlantSite(@PathVariable Integer id){

        if(plantSiteService.deletePlantSite(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Plant site has been deleted with id: " + id));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Could not find Plant site with id: " + id));
    }

    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<?> getPlantSiteByName(@PathVariable String name){
        PlantSite site = plantSiteService.getPlantSiteByName(name);

        if(site == null){
            return ResponseEntity.status(400).body(new ApiResponse("Could not find Plant site with name: " + name));
        }
        return ResponseEntity.status(200).body(site);
    }

    @GetMapping("/get-by-location-keyword/{keyword}")
    public ResponseEntity<?> getPlantSitesByLocationKeyword(@PathVariable String keyword){
        List<PlantSite> sites = plantSiteService.getPlantSitesByLocationKeyword(keyword);

        if(sites.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Could not find Plant sites with location containing: " + keyword));
        }

        return ResponseEntity.status(200).body(sites);
    }

    @GetMapping("/get-available")
    public ResponseEntity<?> getPlantSitesWithAvailableCapacity(){
        List<PlantSite> sites = plantSiteService.getPlantSitesWithAvailableCapacity();

        if(sites.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Could not find Plant sites available capacity"));
        }

        return ResponseEntity.status(200).body(sites);
    }

    @GetMapping("/get-full")
    public ResponseEntity<?> getFullPlantSites(){
        List<PlantSite> sites = plantSiteService.getFullPlantSites();

        if(sites.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Could not find Plant sites that are currently full"));
        }

        return ResponseEntity.status(200).body(sites);
    }

    @GetMapping("/has-available/{id}")
    public ResponseEntity<?> hasAvailableSlots(@PathVariable Integer id){
        if(plantSiteService.hasAvailableSlots(id) != null){
            return ResponseEntity.status(200).body(new ApiResponse("Plant site with id " + id + " has available capacity"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Plant site with id " + id + " has no available capacity or doesn't exist"));
    }

}