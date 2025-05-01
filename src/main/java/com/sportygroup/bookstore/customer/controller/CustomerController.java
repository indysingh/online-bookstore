package com.sportygroup.bookstore.customer.controller;

import com.sportygroup.bookstore.customer.dto.CustomerRequest;
import com.sportygroup.bookstore.customer.dto.CustomerResponse;
import com.sportygroup.bookstore.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling customer-related API requests.
 */
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Customer Management", description = "APIs for managing customers")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Creates and saves a new customer in the system.
     *
     * @param customerRequest the customer request data
     * @return the saved customer response
     */
    @Operation(summary = "Add a new customer", description = "Creates and saves a new customer in the system.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerResponse> addCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        CustomerResponse savedCustomer = customerService.saveCustomer(customerRequest);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    /**
     * Retrieves all customers from the system.
     *
     * @return list of all customer responses
     */
    @Operation(summary = "Get all customers", description = "Returns a list of all customers.")
    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        log.debug("Request to fetch all customers");
        return customerService.getAllCustomers();
    }

    /**
     * Retrieves a specific customer by ID.
     *
     * @param id the ID of the customer
     * @return the customer response
     */
    @Operation(summary = "Get customer by ID", description = "Fetch a customer using their ID.")
    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        log.debug("Fetching customer with ID: {}", id);
        return customerService.getCustomerById(id);
    }

    /**
     * Partially updates a customer's name or email.
     *
     * @param id the ID of the customer to update
     * @param customerRequest the partial update data
     * @return the updated customer response
     */
    @Operation(summary = "Update customer (partial)", description = "Partially update customer's name or email.")
    @PatchMapping("/{id}")
    public CustomerResponse patchCustomer(@PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
        log.info("Received request to patch customer with ID: {}", id);
        return customerService.updateCustomerPartial(id, customerRequest);
    }

    /**
     * Deletes a customer by ID.
     *
     * @param id the ID of the customer to delete
     */
    @Operation(summary = "Delete customer", description = "Deletes a customer from the system using their ID.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long id) {
        log.warn("Request to delete customer with ID: {}", id);
        customerService.deleteCustomer(id);
    }
}
