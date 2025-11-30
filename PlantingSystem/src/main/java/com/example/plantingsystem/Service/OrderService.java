package com.example.plantingsystem.Service;

import com.example.plantingsystem.Model.Company;
import com.example.plantingsystem.Model.Order;
import com.example.plantingsystem.Model.Plant;
import com.example.plantingsystem.Model.Supplier;
import com.example.plantingsystem.Repository.CompanyRepository;
import com.example.plantingsystem.Repository.OrderRepository;
import com.example.plantingsystem.Repository.PlantRepository;
import com.example.plantingsystem.Repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CompanyRepository companyRepository;
    private final PlantRepository plantRepository;
    private final SupplierRepository supplierRepository;

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public int addOrder(Order order){
        Company company = companyRepository.findCompanyById(order.getCompanyId());
        Supplier supplier = supplierRepository.findSupplierById(order.getSupplierId());
        Plant plant = plantRepository.findPlantById(order.getPlantId());

        if(company == null){
            return 2;
        }
        if(!company.getStatus().equalsIgnoreCase("approved")){
            return 6;
        }

        if(supplier == null){
            return 3;
        }

        if(!supplier.getStatus().equalsIgnoreCase("approved")){
            return 4;
        }

        if(plant == null){
            return 5;
        }

        order.setOrderDate(LocalDateTime.now());
        order.setStatus("pending");
        orderRepository.save(order);
        return 1;
    }


    public int updateOrder(Order order, Integer id){
        Order oldOrder = orderRepository.findOrderById(id);
        Company company = companyRepository.findCompanyById(order.getCompanyId());
        Supplier supplier = supplierRepository.findSupplierById(order.getSupplierId());
        Plant plant = plantRepository.findPlantById(order.getPlantId());

        if(oldOrder == null){
            return 2;
        }

        if(company == null){
            return 3;
        }

        if(plant == null){
            return 6;
        }
        if(supplier == null){
            return 4;
        }

        if(!company.getStatus().equalsIgnoreCase("approved")){
            return 7;
        }


        if(!supplier.getStatus().equalsIgnoreCase("approved")){
            return 5;
        }


        oldOrder.setCompanyId(order.getCompanyId());
        oldOrder.setSupplierId(order.getSupplierId());
        oldOrder.setPlantId(order.getPlantId());
        oldOrder.setQuantity(order.getQuantity());
        oldOrder.setOrderDate(order.getOrderDate());

        orderRepository.save(oldOrder);
        return 1;
    }

    public boolean deleteOrder(Integer id){
        Order order = orderRepository.findOrderById(id);

        if(order == null){
            return false;
        }
        orderRepository.delete(order);
        return true;
    }

    public List<Order> getOrdersByCompanyId(Integer companyId){
        return orderRepository.findOrdersByCompanyId(companyId);
    }

    public List<Order> getOrdersBySupplierId(Integer supplierId){
        return orderRepository.findOrdersBySupplierId(supplierId);
    }

    public List<Order> getOrdersByPlantId(Integer plantId){
        return orderRepository.findOrdersByPlantId(plantId);
    }

    public List<Order> getOrdersByQuantity(Integer qty){
        return orderRepository.findOrdersByQuantity(qty);
    }

    public List<Order> getOrdersByOrderDate(LocalDateTime dateTime){
        return orderRepository.findOrdersByOrderDate(dateTime);
    }

    public List<Order> getOrdersByStatus(String status){
        return orderRepository.findOrdersByStatus(status);
    }

}