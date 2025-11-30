package com.example.plantingsystem.Repository;

import com.example.plantingsystem.Model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Integer> {

    Plant findPlantById(Integer id);

    Plant findPlantByName(String name);

    List<Plant> findPlantsBySupplierId(Integer supplierId);

    @Query("select p from Plant p where p.species like %?1%")
    List<Plant> findPlantsBySpeciesKeyword(String keyword);

    @Query("select count(p) from Plant p where p.species = ?1")
    Integer countPlantsBySpecies(String species);

    List<Plant> findPlantsByCategory(String category);

}
