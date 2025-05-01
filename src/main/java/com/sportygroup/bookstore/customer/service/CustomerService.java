package com.sportygroup.bookstore.customer.service;

import com.sportygroup.bookstore.customer.dto.CustomerRequest;
import com.sportygroup.bookstore.customer.dto.CustomerResponse;

import java.util.List;

/**
 * Service interface for managing customer-related operations
 * such as creation, retrieve, update, and delete using DTOs.
 */
public interface CustomerService {

    /**
     * Saves a new customer.
     *
     * @param customerRequest the customer request data
     * @return the saved customer response
     */
    CustomerResponse saveCustomer(CustomerRequest customerRequest);

    /**
     * Retrieves a customer by ID.
     *
     * @param id the ID of the customer
     * @return the customer response
     */
    CustomerResponse getCustomerById(Long id);

    /**
     * Retrieves all customers.
     *
     * @return a list of customer responses
     */
    List<CustomerResponse> getAllCustomers();

    /**
     * Partially updates a customer's fields (name and/or email).
     *
     * @param id               the ID of the customer to update
     * @param customerRequest the customer request data with updated fields
     * @return the updated customer response
     */
    CustomerResponse updateCustomerPartial(Long id, CustomerRequest customerRequest);

    /**
     * Deletes a customer by ID.
     *
     * @param id the ID of the customer to delete
     */
    void deleteCustomer(Long id);
}
