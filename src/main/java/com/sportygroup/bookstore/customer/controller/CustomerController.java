/**
 * CustomerController.java
 *
 * This controller provides REST APIs for managing customers in the Online Bookstore system.
 * It includes functionality to add new customers.
 */

package com.sportygroup.bookstore.customer.controller;

import com.sportygroup.bookstore.customer.model.Customer;
import com.sportygroup.bookstore.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling customer-related API requests.
 */
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Management", description = "APIs for managing customers")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Creates and saves a new customer in the system.
     *
     * @param customer the customer to be created
     * @return the saved customer entity
     */
    @Operation(summary = "Add a new customer", description = "Creates and saves a new customer in the system.")
    @PostMapping
    public Customer addCustomer(@RequestBody @Valid Customer customer) {
        return customerService.saveCustomer(customer);
    }
}
