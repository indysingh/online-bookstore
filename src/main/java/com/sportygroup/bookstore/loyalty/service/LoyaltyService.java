package com.sportygroup.bookstore.loyalty.service;

import com.sportygroup.bookstore.customer.model.Customer;
import com.sportygroup.bookstore.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoyaltyService {
    private final CustomerRepository customerRepository;

    public int getLoyaltyPoints(Long customerId) {
        return customerRepository.findById(customerId)
                .map(Customer::getLoyaltyPoints)
                .orElse(0);
    }
}
