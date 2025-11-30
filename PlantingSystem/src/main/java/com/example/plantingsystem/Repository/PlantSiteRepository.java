package com.example.plantingsystem.Repository;

import com.example.plantingsystem.Model.PlantSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantSiteRepository extends JpaRepository < PlantSite, Integer> {
    PlantSite findPlantSiteById(Integer id);

    PlantSite findPlantSiteByName(String name);


    @Query("select p from PlantSite p where p.location like %?1%")
    List<PlantSite> findPlantSitesByLocationKeyword(String keyword);


    @Query("select p from PlantSite p where p.plantsCapacity > p.currentPlanted")
    List<PlantSite> findPlantSitesWithAvailableCapacity();


    @Query("select p from PlantSite p where p.plantsCapacity = p.currentPlanted")
    List<PlantSite> findFullPlantSites();


    @Query("select p from PlantSite p where p.id = ?1 and p.plantsCapacity > p.currentPlanted")
    PlantSite findPlantSiteIfHasAvailableCapacity(Integer id);
}
