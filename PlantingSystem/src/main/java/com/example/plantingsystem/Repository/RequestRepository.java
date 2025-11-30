package com.example.plantingsystem.Repository;

import com.example.plantingsystem.Model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    Request findRequestById(Integer id);

    @Query("select r from Request r where r.companyId = ?1")
    List<Request> findRequestsByCompanyId(Integer companyId);

    @Query("select r from Request r where r.customerId = ?1")
    List<Request> findRequestsByCustomerId(Integer customerId);

    @Query("select r from Request r order by r.numberOfPlants desc")
    Request findMaxNumberOfPlants();
    @Query("select r from Request r where r.numberOfPlants >= ?1")
    List<Request> findRequestsByNumberOfPlants(Integer number);

    List<Request> findRequestsByStatus(String status);

    List<Request> findRequestsByRequestedAt(LocalDateTime requestedAt);

    List<Request> findRequestsByScheduledDate(LocalDate scheduledDate);

}
