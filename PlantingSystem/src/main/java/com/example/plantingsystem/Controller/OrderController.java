package com.example.plantingsystem.Controller;

import com.example.plantingsystem.Api.ApiResponse;
import com.example.plantingsystem.Model.Order;
import com.example.plantingsystem.Service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllOrders(){
        return ResponseEntity.status(200).body(orderService.getAllOrders());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@RequestBody @Valid Order order,Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        int result = orderService.addOrder(order);

        if(result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("could not find Company with id: " + order.getCompanyId()));
        }
        if(result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("could not find Supplier with id: " + order.getSupplierId()));
        }
        if(result == 4){
            return ResponseEntity.status(400).body(new ApiResponse("Supplier with id: " + order.getSupplierId() + " is not approved"));
        }
        if(result == 5){
            return ResponseEntity.status(400).body(new ApiResponse("could not find Plant with id: " + order.getPlantId()));
        }
        if(result == 6){
            return ResponseEntity.status(400).body(new ApiResponse("Company for this order is not approved"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Order has been added."));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrder(@RequestBody @Valid Order order, @PathVariable Integer id,Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        int result = orderService.updateOrder(order, id);

        if(result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("Order not found with id: " + id));
        }
        if(result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("could not find Company with id: " + order.getCompanyId()));
        }
        if(result == 4){
            return ResponseEntity.status(400).body(new ApiResponse("could not find Supplier with id: " + order.getSupplierId()));
        }
        if(result == 5){
            return ResponseEntity.status(400).body(new ApiResponse("Supplier with id: " + order.getSupplierId() + " is not approved"));
        }
        if(result == 7){
            return ResponseEntity.status(400).body(new ApiResponse("Company with id: " + order.getCompanyId() + " is not approved"));
        }
        if(result == 6){
            return ResponseEntity.status(400).body(new ApiResponse("could not find Plant with id: " + order.getPlantId()));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Order has been updated with id: " + id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer id){

        if(orderService.deleteOrder(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Order has been deleted with id: " + id));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Couldn't find Order with id: " + id));
    }


    @GetMapping("/get-by-company/{companyId}")
    public ResponseEntity<?> getOrdersByCompany(@PathVariable Integer companyId){
        List<Order> orders = orderService.getOrdersByCompanyId(companyId);

        if(orders.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find orders with company id: " + companyId));
        }
        return ResponseEntity.status(200).body(orders);
    }

    @GetMapping("/get-by-supplier/{supplierId}")
    public ResponseEntity<?> getOrdersBySupplier(@PathVariable Integer supplierId){
        List<Order> orders = orderService.getOrdersBySupplierId(supplierId);

        if(orders.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find orders with supplier id: " + supplierId));
        }
        return ResponseEntity.status(200).body(orders);
    }

    @GetMapping("/get-by-plant/{plantId}")
    public ResponseEntity<?> getOrdersByPlant(@PathVariable Integer plantId){
        List<Order> orders = orderService.getOrdersByPlantId(plantId);

        if(orders.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find orders with plant id: " + plantId));
        }
        return ResponseEntity.status(200).body(orders);
    }

    @GetMapping("/get-by-quantity/{qty}")
    public ResponseEntity<?> getOrdersByMinQuantity(@PathVariable Integer qty){
        List<Order> orders = orderService.getOrdersByQuantity(qty);

        if(orders.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find orders with quantity: " + qty +", or more"));
        }
        return ResponseEntity.status(200).body(orders);
    }

    @GetMapping("/get-by-date/{dateTime}")
    public ResponseEntity<?> getOrdersByOrderDate(@PathVariable LocalDateTime dateTime){
        List<Order> orders = orderService.getOrdersByOrderDate(dateTime);

        if(orders.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find orders with orderDate: " + dateTime));
        }
        return ResponseEntity.status(200).body(orders);
    }

    @GetMapping("/get-by-status/{status}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable String status){
        List<Order> orders = orderService.getOrdersByStatus(status);

        if(orders.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Couldn't find orders with status: " + status));
        }
        return ResponseEntity.status(200).body(orders);
    }
}