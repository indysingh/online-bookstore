/**
 * BookController.java
 *
 * This controller handles all book-related operations including:
 * - Adding new books to the store.
 * - Fetching available books with pagination.
 * - Purchasing books.
 * - Retrieving customer loyalty points.
 *
 * Developed as part of the Online Bookstore backend system.
 */

package com.sportygroup.bookstore.book.controller;

import com.sportygroup.bookstore.purchase.dto.PurchaseRequest;
import com.sportygroup.bookstore.purchase.dto.PurchaseResponse;
import com.sportygroup.bookstore.book.model.Book;
import com.sportygroup.bookstore.book.service.BookService;
import com.sportygroup.bookstore.loyalty.service.LoyaltyService;
import com.sportygroup.bookstore.service.PurchaseServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling book-related API requests.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Book Management", description = "APIs for managing books, purchasing, and loyalty points")
public class BookController {

    private final BookService bookService;
    private final PurchaseServiceImpl purchaseService;
    private final LoyaltyService loyaltyService;

    /**
     * Adds a list of books to the bookstore inventory.
     *
     * @param books list of books to add
     * @return list of saved books
     */
    @Operation(summary = "Add multiple books", description = "Adds a list of books to the bookstore inventory.")
    @PostMapping("/books")
    public List<Book> addBooks(@RequestBody @Valid List<Book> books) {
        return bookService.addBooks(books);
    }

    /**
     * Retrieves a paginated list of all available books.
     *
     * @param page current page number (default is 0)
     * @param size number of items per page (default is 10)
     * @return paginated list of books
     */
    @Operation(summary = "Get paginated list of books", description = "Retrieves a paginated list of available books.")
    @GetMapping("/books")
    public Page<Book> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return bookService.getAllBooks(pageable);
    }

    /**
     * Allows a customer to purchase books by providing book IDs and their customer ID.
     *
     * @param request purchase request DTO
     * @return purchase response including total price and loyalty info
     */
    @Operation(summary = "Purchase books", description = "Allows a customer to purchase a list of books by providing book IDs and their customer ID.")
    @PostMapping("/purchase")
    public PurchaseResponse purchaseBooks(@RequestBody @Valid PurchaseRequest request) {
        return purchaseService.purchaseBooks(request.bookIds(), request.customerId());
    }

    /**
     * Fetches the current loyalty points for a specific customer.
     *
     * @param customerId unique ID of the customer
     * @return total loyalty points
     */
    @Operation(summary = "Get loyalty points", description = "Fetches the current loyalty points for a specific customer.")
    @GetMapping("/loyalty/{customerId}")
    public int getLoyaltyPoints(@PathVariable Long customerId) {
        return loyaltyService.getLoyaltyPoints(customerId);
    }
}
