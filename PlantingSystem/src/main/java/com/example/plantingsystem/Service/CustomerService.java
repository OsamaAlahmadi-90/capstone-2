package com.example.plantingsystem.Service;

import com.example.plantingsystem.Model.Customer;
import com.example.plantingsystem.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PlantAiService plantAiService;


    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public void addCustomer(Customer customer){
        customer.setRegistrationDate(LocalDate.now());
        customerRepository.save(customer);
    }

    public boolean updateCustomer(Customer customer, Integer id){
        Customer oldCustomer = customerRepository.findCustomerById(id);
        if(oldCustomer == null){
            return false;
        }

        oldCustomer.setName(customer.getName());
        oldCustomer.setEmail(customer.getEmail());
        oldCustomer.setPassword(customer.getPassword());
        oldCustomer.setPhone(customer.getPhone());
        oldCustomer.setRegistrationDate(customer.getRegistrationDate());

        customerRepository.save(oldCustomer);
        return true;
    }

    public boolean deleteCustomer(Integer id){
        Customer customer = customerRepository.findCustomerById(id);
        if(customer == null){
            return false;
        }
        customerRepository.delete(customer);
        return true;
    }

    public Customer getCustomerByName(String name){

        return customerRepository.findCustomerByName(name);
    }

    public Customer getCustomerByPhone(String phone){

        return customerRepository.findCustomerByPhone(phone);
    }

    public List<Customer> getCustomersBeforeDate(LocalDate date){
        return customerRepository.findCustomersByRegistrationBeforeDate(date);
    }

    public List<Customer> getCustomersAfterDate(LocalDate date){
        return customerRepository.findCustomersByRegistrationAfterDate(date);
    }




    public String aiPlantCareAdvice(String plantName) {
        return plantAiService.getPlantCareAdvice(plantName);
    }

    public String aiPlantSuggestionsByLocation(String text) {
        return plantAiService.suggestPlantsByLocationAndWeather(text);
    }

    public String aiPlantInfo(String plantName) {
        return plantAiService.getPlantInfo(plantName);
    }
}
