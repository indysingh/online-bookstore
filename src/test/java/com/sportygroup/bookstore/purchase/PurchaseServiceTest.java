package com.sportygroup.bookstore.purchase;

import com.sportygroup.bookstore.book.dto.BookType;
import com.sportygroup.bookstore.purchase.dto.PurchaseResponse;
import com.sportygroup.bookstore.book.model.Book;
import com.sportygroup.bookstore.customer.model.Customer;
import com.sportygroup.bookstore.book.repository.BookRepository;
import com.sportygroup.bookstore.customer.repository.CustomerRepository;
import com.sportygroup.bookstore.service.PurchaseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    @Test
    void givenSufficientLoyaltyPoints_whenPurchaseBooks_thenTotalIsZeroAndPointsDeducted() {
        // Given
        Customer customer = new Customer(1L, "John", "john@example.com", 40);
        Book book = new Book("Clean Code", BookType.REGULAR, 40.0);

        when(bookRepository.findAllById(List.of(1L))).thenReturn(List.of(book));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any())).thenReturn(customer);

        // When
        PurchaseResponse response = purchaseService.purchaseBooks(List.of(1L), 1L);

        // Then
        assertEquals(0.0, response.totalPrice(), 0.01); // free due to loyalty
        assertEquals(1, response.remainingLoyaltyPoints()); // 40 - 40 + 1
    }

    @Test
    void givenCustomerDoesNotExist_whenPurchaseBooks_thenShouldThrowException() {
        // Given
        when(bookRepository.findAllById(List.of(1L))).thenReturn(List.of());
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(RuntimeException.class, () -> {
            purchaseService.purchaseBooks(List.of(1L), 1L);
        });
    }

    @Test
    void givenInsufficientLoyaltyPoints_whenPurchaseBooks_thenShouldApplyPartialDiscount() {
        // Given
        Customer customer = new Customer(1L, "Jane", "jane@example.com", 5); // 5 points
        Book book = new Book("Refactoring", BookType.REGULAR, 15.0); // $15

        when(bookRepository.findAllById(List.of(1L))).thenReturn(List.of(book));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any())).thenReturn(customer);

        // When
        PurchaseResponse response = purchaseService.purchaseBooks(List.of(1L), 1L);

        // Then
        assertEquals(10.0, response.totalPrice(), 0.01); // 5 loyalty points = $5 discount
        assertEquals(1, response.remainingLoyaltyPoints()); // 5 - 5 + 1
    }

    @Test
    void givenNoBooksFound_whenPurchaseBooks_thenShouldReturnZeroTotal() {
        // Given
        Customer customer = new Customer(1L, "Mike", "mike@example.com", 20);
        when(bookRepository.findAllById(List.of())).thenReturn(List.of());
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // When
        PurchaseResponse response = purchaseService.purchaseBooks(List.of(), 1L);

        // Then
        assertEquals(0.0, response.totalPrice(), 0.01);
        assertEquals(20, response.remainingLoyaltyPoints()); // unchanged
    }

    @Test
    void givenBooksCostLessThanPoints_whenPurchaseBooks_thenNoCostAndPointsRemain() {
        // Given
        Customer customer = new Customer(1L, "Anna", "anna@example.com", 50);
        Book book1 = new Book("Book1", BookType.REGULAR, 10.0);
        Book book2 = new Book("Book2", BookType.REGULAR, 5.0); // Total = $15

        when(bookRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(book1, book2));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any())).thenReturn(customer);

        // When
        PurchaseResponse response = purchaseService.purchaseBooks(List.of(1L, 2L), 1L);

        // Then
        assertEquals(0.0, response.totalPrice(), 0.01);
        assertEquals(37, response.remainingLoyaltyPoints()); // 50 - 15 + 2
    }
}
