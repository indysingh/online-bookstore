package com.sportygroup.bookstore.purchase;

import com.sportygroup.bookstore.book.model.Book;

public interface PriceCalculator {
    double calculatePrice(Book book, int quantity, boolean isBundle);
}
