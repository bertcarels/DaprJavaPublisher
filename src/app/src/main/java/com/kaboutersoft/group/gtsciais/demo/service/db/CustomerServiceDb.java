package com.kaboutersoft.group.gtsciais.demo.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaboutersoft.group.gtsciais.demo.model.Customer;
import com.kaboutersoft.group.gtsciais.demo.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerServiceDb {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceDb(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        if (!customerRepository.existsById(id)) {
            return null;
        }
        updatedCustomer.setId(id);
        return customerRepository.save(updatedCustomer);
    }

    public boolean deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            return false;
        }
        customerRepository.deleteById(id);
        return true;
    }
}
