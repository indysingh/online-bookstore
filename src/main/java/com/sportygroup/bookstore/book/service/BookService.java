package com.sportygroup.bookstore.book.service;

import com.sportygroup.bookstore.book.model.Book;
import com.sportygroup.bookstore.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> addBooks(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
}
