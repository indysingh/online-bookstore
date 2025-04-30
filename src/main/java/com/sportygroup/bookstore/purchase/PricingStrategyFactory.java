package com.sportygroup.bookstore.purchase;

import com.sportygroup.bookstore.book.dto.BookType;

public class PricingStrategyFactory {
    public static PriceCalculator getStrategy(BookType type) {
        return switch (type) {
            case NEW_RELEASE -> new NewReleaseCalculator();
            case REGULAR -> new RegularCalculator();
            case OLD_EDITION -> new OldEditionCalculator();
        };
    }
}