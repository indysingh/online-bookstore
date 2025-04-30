package com.sportygroup.bookstore.customer.repository;

import com.sportygroup.bookstore.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}
