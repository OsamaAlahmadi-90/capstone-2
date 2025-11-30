package com.example.plantingsystem.Repository;

import com.example.plantingsystem.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findOrderById(Integer id);

    List<Order> findOrdersByCompanyId(Integer companyId);

    List<Order> findOrdersBySupplierId(Integer supplierId);

    List<Order> findOrdersByPlantId(Integer plantId);

    @Query("select o from Order o where o.quantity >= ?1")
    List<Order> findOrdersByQuantity(Integer quantity);

    List<Order> findOrdersByOrderDate(LocalDateTime orderDate);

    List<Order> findOrdersByStatus(String status);

}
