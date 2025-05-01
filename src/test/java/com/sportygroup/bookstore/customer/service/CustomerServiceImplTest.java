package com.sportygroup.bookstore.customer.service;

import com.sportygroup.bookstore.customer.dto.CustomerRequest;
import com.sportygroup.bookstore.customer.dto.CustomerResponse;
import com.sportygroup.bookstore.customer.mapper.CustomerMapper;
import com.sportygroup.bookstore.customer.model.Customer;
import com.sportygroup.bookstore.customer.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerMapper = new CustomerMapper(); // assuming it's a real class, not an interface
        customerService = new CustomerServiceImpl(customerRepository, customerMapper);
    }

    @Test
    void givenCustomerRequest_whenSaveCustomer_thenReturnsCustomerResponse() {
        CustomerRequest request = new CustomerRequest("Alice", "alice@example.com");
        Customer savedCustomer = new Customer(1L, "Alice", "alice@example.com", 0);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerResponse response = customerService.saveCustomer(request);

        assertEquals("Alice", response.name());
        assertEquals("alice@example.com", response.email());
        assertEquals(0, response.loyaltyPoints());
    }

    @Test
    void givenCustomerExists_whenGetCustomerById_thenReturnsCustomerResponse() {
        Customer customer = new Customer(1L, "Bob", "bob@example.com", 10);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerResponse response = customerService.getCustomerById(1L);

        assertEquals("Bob", response.name());
        assertEquals(10, response.loyaltyPoints());
    }

    @Test
    void givenCustomerNotExists_whenGetCustomerById_thenThrowsException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> customerService.getCustomerById(1L));
    }

    @Test
    void whenGetAllCustomers_thenReturnsCustomerList() {
        List<Customer> customers = List.of(
                new Customer(1L, "A", "a@example.com", 5),
                new Customer(2L, "B", "b@example.com", 7)
        );
        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerResponse> responseList = customerService.getAllCustomers();

        assertEquals(2, responseList.size());
        assertEquals("A", responseList.get(0).name());
    }

    @Test
    void givenPartialUpdate_whenUpdateCustomer_thenReturnsUpdatedResponse() {
        Customer existing = new Customer(1L, "Old Name", "old@example.com", 20);
        CustomerRequest patch = new CustomerRequest("New Name", null);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(customerRepository.save(any(Customer.class))).thenReturn(existing);

        CustomerResponse response = customerService.updateCustomerPartial(1L, patch);

        assertEquals("New Name", response.name());
    }

    @Test
    void givenCustomerExists_whenDeleteCustomer_thenDeletesSuccessfully() {
        when(customerRepository.existsById(1L)).thenReturn(true);

        customerService.deleteCustomer(1L);

        verify(customerRepository).deleteById(1L);
    }

    @Test
    void givenCustomerNotFound_whenDeleteCustomer_thenThrowsException() {
        when(customerRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> customerService.deleteCustomer(1L));
    }
}
