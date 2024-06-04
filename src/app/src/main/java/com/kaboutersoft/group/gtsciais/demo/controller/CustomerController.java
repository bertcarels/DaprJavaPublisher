package com.kaboutersoft.group.gtsciais.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kaboutersoft.group.gtsciais.demo.model.Customer;
import com.kaboutersoft.group.gtsciais.demo.service.db.CustomerServiceDb;
import com.kaboutersoft.group.gtsciais.demo.service.publish.CustomerServicePubSub;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerServiceDb customerServiceDb;
    private final CustomerServicePubSub customerServicePubSub;


    @Autowired
    public CustomerController(CustomerServiceDb customerServiceDb, CustomerServicePubSub customerServicePubSub) {
        this.customerServiceDb = customerServiceDb;
        this.customerServicePubSub = customerServicePubSub;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerServiceDb.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerServiceDb.getCustomerById(id);
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        Customer c = customerServiceDb.createCustomer(customer);
        logger.info("Customer created: " + c.getId());

        c = customerServicePubSub.publishCustomerEvent(c);
        logger.info("Customer Event published:" + c.getId());

        return c;
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        return customerServiceDb.updateCustomer(id, updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCustomer(@PathVariable Long id) {
        return customerServiceDb.deleteCustomer(id);
    }
}
