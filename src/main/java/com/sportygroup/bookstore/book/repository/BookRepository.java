package com.sportygroup.bookstore.book.repository;

import com.sportygroup.bookstore.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}
