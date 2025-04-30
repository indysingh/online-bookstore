package com.sportygroup.bookstore.purchase;

import com.sportygroup.bookstore.book.model.Book;

public class OldEditionCalculator implements PriceCalculator {
    public double calculatePrice(Book book, int quantity, boolean isBundle) {
        double price = book.getBasePrice() * 0.8;
        if (isBundle) {
            price *= 0.95; // additional 5% off
        }
        return price * quantity;
    }
}