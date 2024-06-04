package com.kaboutersoft.group.gtsciais.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaboutersoft.group.gtsciais.demo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Define additional methods if needed
}
