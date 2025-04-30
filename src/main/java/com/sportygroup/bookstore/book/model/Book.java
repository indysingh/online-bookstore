package com.sportygroup.bookstore.book.model;

import com.sportygroup.bookstore.book.dto.BookType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private BookType type;

    private double basePrice;

    // Optional custom constructor (not needed by JPA, just for your convenience)
    public Book(String title, BookType type, double basePrice) {
        this.title = title;
        this.type = type;
        this.basePrice = basePrice;
    }
}
