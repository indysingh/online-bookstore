package com.sportygroup.bookstore.service;

import com.sportygroup.bookstore.book.dto.BookType;
import com.sportygroup.bookstore.purchase.dto.PurchaseResponse;
import com.sportygroup.bookstore.book.model.Book;
import com.sportygroup.bookstore.customer.model.Customer;
import com.sportygroup.bookstore.book.repository.BookRepository;
import com.sportygroup.bookstore.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private PurchaseService purchaseService;

    @Test
    void shouldCalculateTotalWithLoyaltyPointsApplied() {
        Customer customer = new Customer(1L, "john@example.com", 10);
        Book book = new Book("Clean Code", BookType.REGULAR, 40.0);

        Mockito.when(bookRepository.findAllById(List.of(1L))).thenReturn(List.of(book));
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        PurchaseResponse response = purchaseService.purchaseBooks(List.of(1L), 1L);

        assertEquals(0.0, response.totalPrice(), 0.01); // free due to loyalty
        assertEquals(0, response.remainingLoyaltyPoints());
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        Mockito.when(bookRepository.findAllById(List.of(1L))).thenReturn(List.of());
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            purchaseService.purchaseBooks(List.of(1L), 1L);
        });
    }
}
