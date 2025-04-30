package com.sportygroup.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportygroup.bookstore.book.controller.BookController;
import com.sportygroup.bookstore.book.dto.BookType;
import com.sportygroup.bookstore.purchase.dto.PurchaseResponse;
import com.sportygroup.bookstore.book.model.Book;
import com.sportygroup.bookstore.book.service.BookService;
import com.sportygroup.bookstore.loyalty.service.LoyaltyService;
import com.sportygroup.bookstore.service.PurchaseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private PurchaseService purchaseService;

    @MockBean
    private LoyaltyService loyaltyService;

    @Test
    void shouldReturnListOfBooksWithPagination() throws Exception {
        Book book1 = new Book(1L, "Test Book 1", BookType.REGULAR, 50.0);
        Book book2 = new Book(2L, "Test Book 2", BookType.NEW_RELEASE, 70.0);
        Page<Book> pagedBooks = new PageImpl<>(List.of(book1, book2), PageRequest.of(0, 10), 2);

        Mockito.when(bookService.getAllBooks(any())).thenReturn(pagedBooks);

        mockMvc.perform(get("/api/books?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Test Book 1"))
                .andExpect(jsonPath("$.content[1].title").value("Test Book 2"))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.size").value(10));
    }

    @Test
    void shouldReturnLoyaltyPoints() throws Exception {
        Mockito.when(loyaltyService.getLoyaltyPoints(1L)).thenReturn(7);

        mockMvc.perform(get("/api/loyalty/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("7"));
    }

    @Test
    void shouldReturnPurchaseResponse() throws Exception {
        PurchaseResponse response = new PurchaseResponse(100.0, 5);
        Mockito.when(purchaseService.purchaseBooks(any(), any()))
                .thenReturn(response);

        String jsonRequest = objectMapper.writeValueAsString(
                new com.sportygroup.bookstore.purchase.dto.PurchaseRequest(List.of(1L, 2L), 1L)
        );

        mockMvc.perform(post("/api/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(100.0))
                .andExpect(jsonPath("$.remainingLoyaltyPoints").value(5));
    }
}
