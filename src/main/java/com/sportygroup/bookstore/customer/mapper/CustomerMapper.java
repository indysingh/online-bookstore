package com.sportygroup.bookstore.customer.mapper;

import com.sportygroup.bookstore.customer.dto.CustomerRequest;
import com.sportygroup.bookstore.customer.dto.CustomerResponse;
import com.sportygroup.bookstore.customer.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequest request) {
        return new Customer(null, request.name(), request.email(), 0);
    }

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getLoyaltyPoints()
        );
    }
}
