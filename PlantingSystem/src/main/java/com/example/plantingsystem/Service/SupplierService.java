package com.example.plantingsystem.Service;

import com.example.plantingsystem.Model.Company;
import com.example.plantingsystem.Model.Order;
import com.example.plantingsystem.Model.Supplier;
import com.example.plantingsystem.Repository.CompanyRepository;
import com.example.plantingsystem.Repository.OrderRepository;
import com.example.plantingsystem.Repository.PlantRepository;
import com.example.plantingsystem.Repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final OrderRepository orderRepository;
    private final CompanyRepository companyRepository;
    private final PlantRepository plantRepository;

    public List<Supplier> getAllSuppliers(){

        return supplierRepository.findAll();

    }

    public void addSupplier(Supplier supplier){

        supplier.setStatus("pending");
        supplierRepository.save(supplier);

    }

    public boolean updateSupplier(Supplier supplier, Integer id){
        Supplier oldSupplier = supplierRepository.findSupplierById(id);

        if(oldSupplier == null){
            return false;
        }


        oldSupplier.setName(supplier.getName());
        oldSupplier.setEmail(supplier.getEmail());
        oldSupplier.setPhone(supplier.getPhone());
        oldSupplier.setStatus(supplier.getStatus());

        supplierRepository.save(oldSupplier);
        return true;
    }

    public boolean deleteSupplier(Integer id){

        Supplier supplier = supplierRepository.findSupplierById(id);

        if(supplier == null){
            return false;
        }

        supplierRepository.delete(supplier);
        return true;
    }

    public Supplier getSupplierByName(String name){
        return supplierRepository.findSupplierByName(name);
    }

    public List<Supplier> getSuppliersByStatus(String status){
        return supplierRepository.findSuppliersByStatus(status);
    }


    public int approveOrder(Integer orderId){
        Order order = orderRepository.findOrderById(orderId);
        Company company = companyRepository.findCompanyById(order.getCompanyId());
        Supplier supplier = supplierRepository.findSupplierById(order.getSupplierId());

        if(order == null){
            return 2;
        }

        if(company == null){
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

        if(!order.getStatus().equalsIgnoreCase("pending")){
            return 3;
        }




        order.setStatus("approved");
        orderRepository.save(order);
        return 1;
    }


    public int completeOrder(Integer orderId){
        Order order = orderRepository.findOrderById(orderId);
        if(order == null){
            return 2;
        }

        if(!order.getStatus().equalsIgnoreCase("approved")){
            return 3;
        }

        order.setStatus("completed");
        orderRepository.save(order);
        return 1;
    }

    public int rejectOrder(Integer orderId){
        Order order = orderRepository.findOrderById(orderId);
        if(order == null){
            return 2;
        }

        String status = order.getStatus().toLowerCase();
        if(!status.equals("pending")){
            return 3;
        }

        order.setStatus("rejected");
        orderRepository.save(order);
        return 1;

    }

}
