package com.sportygroup.bookstore.book.service;

import com.sportygroup.bookstore.book.dto.BookType;
import com.sportygroup.bookstore.book.model.Book;
import com.sportygroup.bookstore.book.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void givenBooks_whenAddBooks_thenReturnSavedBooks() {
        // Given
        List<Book> books = List.of(
                new Book(null, "Book 1", BookType.REGULAR, 25.0),
                new Book(null, "Book 2", BookType.NEW_RELEASE, 30.0)
        );
        when(bookRepository.saveAll(books)).thenReturn(books);

        // When
        List<Book> result = bookService.addBooks(books);

        // Then
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).saveAll(books);
    }

    @Test
    void givenPageable_whenGetAllBooks_thenReturnPaginatedBooks() {
        // Given
        Pageable pageable = PageRequest.of(0, 2);
        List<Book> books = List.of(
                new Book(1L, "Book A", BookType.REGULAR, 20.0),
                new Book(2L, "Book B", BookType.OLD_EDITION, 15.0)
        );
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);

        // When
        Page<Book> result = bookService.getAllBooks(pageable);

        // Then
        assertEquals(2, result.getTotalElements());
        assertEquals("Book A", result.getContent().get(0).getTitle());
    }

    @Test
    void givenEmptyRepository_whenGetAllBooks_thenReturnEmptyPage() {
        // Given
        Pageable pageable = PageRequest.of(0, 2);
        Page<Book> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(bookRepository.findAll(pageable)).thenReturn(emptyPage);

        // When
        Page<Book> result = bookService.getAllBooks(pageable);

        // Then
        assertTrue(result.isEmpty());
        assertEquals(0, result.getTotalElements());
    }
}
