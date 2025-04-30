package com.sportygroup.bookstore.purchase;

import com.sportygroup.bookstore.book.model.Book;

public class NewReleaseCalculator implements PriceCalculator {
    public double calculatePrice(Book book, int quantity, boolean isBundle) {
        return book.getBasePrice() * quantity;
    }
}