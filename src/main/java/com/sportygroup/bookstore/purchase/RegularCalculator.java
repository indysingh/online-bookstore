package com.sportygroup.bookstore.purchase;

import com.sportygroup.bookstore.book.model.Book;

public class RegularCalculator implements PriceCalculator {
    public double calculatePrice(Book book, int quantity, boolean isBundle) {
        double price = book.getBasePrice();
        if (isBundle) {
            price *= 0.9; // 10% off
        }
        return price * quantity;
    }
}
