package com.sportygroup.bookstore.customer.service;

import com.sportygroup.bookstore.customer.dto.CustomerRequest;
import com.sportygroup.bookstore.customer.dto.CustomerResponse;
import com.sportygroup.bookstore.customer.mapper.CustomerMapper;
import com.sportygroup.bookstore.customer.model.Customer;
import com.sportygroup.bookstore.customer.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of CustomerService for managing customer-related operations
 * such as create, retrieve, update, and delete using DTOs.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    /**
     * Saves a new customer to the database.
     *
     * @param customerRequest the customer request data
     * @return the saved customer response
     */
    @Override
    public CustomerResponse saveCustomer(CustomerRequest customerRequest) {
        // Convert CustomerRequest to Customer entity
        Customer customer = customerMapper.toEntity(customerRequest);

        // Save customer to repository
        Customer savedCustomer = customerRepository.save(customer);

        // Convert the saved Customer entity to a CustomerResponse
        return customerMapper.toResponse(savedCustomer);
    }

    /**
     * Retrieves a customer by ID.
     *
     * @param id the ID of the customer
     * @return the customer response
     * @throws EntityNotFoundException if no customer is found with the given ID
     */
    @Override
    public CustomerResponse getCustomerById(Long id) {
        log.debug("Fetching customer with ID: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Customer not found with ID: {}", id);
                    return new EntityNotFoundException("Customer not found with id: " + id);
                });

        return customerMapper.toResponse(customer);
    }

    /**
     * Retrieves all customers from the database.
     *
     * @return a list of all customer responses
     */
    @Override
    public List<CustomerResponse> getAllCustomers() {
        log.debug("Fetching all customers");
        return customerRepository.findAll().stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Partially updates a customer's fields (name and/or email).
     *
     * @param id               the ID of the customer to update
     * @param customerRequest the customer request data with updated fields
     * @return the updated customer response
     * @throws EntityNotFoundException if the customer does not exist
     */
    @Override
    public CustomerResponse updateCustomerPartial(Long id, CustomerRequest customerRequest) {
        log.info("Partially updating customer with ID: {}", id);
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));

        if (customerRequest.name() != null && !customerRequest.name().isBlank()) {
            log.debug("Updating name to: {}", customerRequest.name());
            existing.setName(customerRequest.name());
        }

        if (customerRequest.email() != null && !customerRequest.email().isBlank()) {
            log.debug("Updating email to: {}", customerRequest.email());
            existing.setEmail(customerRequest.email());
        }

        Customer updated = customerRepository.save(existing);
        return customerMapper.toResponse(updated);
    }

    /**
     * Deletes a customer by ID.
     *
     * @param id the ID of the customer to delete
     */
    @Override
    public void deleteCustomer(Long id) {
        log.info("Attempting to delete customer with ID: {}", id);
        if (!customerRepository.existsById(id)) {
            log.error("Customer not found with ID: {}", id);
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
        log.info("Customer with ID {} deleted successfully", id);
    }
}
