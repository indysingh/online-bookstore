package com.sportygroup.bookstore.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportygroup.bookstore.customer.dto.CustomerRequest;
import com.sportygroup.bookstore.customer.dto.CustomerResponse;
import com.sportygroup.bookstore.customer.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenValidCustomer_whenPost_thenShouldReturnSavedCustomer() throws Exception {
        CustomerRequest customerRequest = new CustomerRequest("John Doe", "john@example.com");
        CustomerResponse customerResponse = new CustomerResponse(1L, "John Doe", "john@example.com", 0);

        Mockito.when(customerService.saveCustomer(any(CustomerRequest.class))).thenReturn(customerResponse);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.loyaltyPoints").value(0));
    }

    @Test
    void givenCustomersExist_whenGetAll_thenShouldReturnList() throws Exception {
        List<CustomerResponse> customerResponses = List.of(
                new CustomerResponse(1L, "John Doe", "john@example.com", 5),
                new CustomerResponse(2L, "Jane Smith", "jane@example.com", 10)
        );

        Mockito.when(customerService.getAllCustomers()).thenReturn(customerResponses);

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void givenCustomerExists_whenGetById_thenShouldReturnCustomer() throws Exception {
        CustomerResponse customerResponse = new CustomerResponse(1L, "John Doe", "john@example.com", 5);
        Mockito.when(customerService.getCustomerById(1L)).thenReturn(customerResponse);

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.loyaltyPoints").value(5));
    }

    @Test
    void givenPartialUpdate_whenPatchCustomer_thenShouldReturnUpdatedCustomer() throws Exception {
        CustomerRequest partialRequest = new CustomerRequest("John Doe", "newemail@example.com");
        CustomerResponse updatedCustomer = new CustomerResponse(1L, "John Doe", "newemail@example.com", 5);

        Mockito.when(customerService.updateCustomerPartial(Mockito.eq(1L), any(CustomerRequest.class)))
                .thenReturn(updatedCustomer);

        mockMvc.perform(patch("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partialRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("newemail@example.com"))
                .andExpect(jsonPath("$.loyaltyPoints").value(5));
    }

    @Test
    void givenCustomerExists_whenDelete_thenShouldSucceed() throws Exception {
        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(customerService).deleteCustomer(1L);
    }

    @Test
    void givenInvalidPayload_whenPost_thenShouldReturnBadRequest() throws Exception {
        String invalidJson = "{}"; // missing name and email

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}
