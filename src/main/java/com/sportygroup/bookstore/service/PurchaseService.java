package com.sportygroup.bookstore.service;

import com.sportygroup.bookstore.book.dto.BookType;
import com.sportygroup.bookstore.purchase.dto.PurchaseResponse;
import com.sportygroup.bookstore.book.model.Book;
import com.sportygroup.bookstore.customer.model.Customer;
import com.sportygroup.bookstore.purchase.PriceCalculator;
import com.sportygroup.bookstore.purchase.PricingStrategyFactory;
import com.sportygroup.bookstore.book.repository.BookRepository;
import com.sportygroup.bookstore.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;

    public PurchaseResponse purchaseBooks(List<Long> bookIds, Long customerId) {
        List<Book> books = bookRepository.findAllById(bookIds);
        Map<BookType, Long> typeCounts = books.stream()
                .collect(Collectors.groupingBy(Book::getType, Collectors.counting()));

        double totalPrice = 0;
        for (Book book : books) {
            PriceCalculator calculator = PricingStrategyFactory.getStrategy(book.getType());
            boolean isBundle = typeCounts.get(book.getType()) >= 3;
            totalPrice += calculator.calculatePrice(book, 1, isBundle);
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.addLoyaltyPoints(books.size());

        if (customer.getLoyaltyPoints() >= 10) {
            double cheapest = books.stream()
                    .filter(b -> b.getType() != BookType.NEW_RELEASE)
                    .mapToDouble(Book::getBasePrice)
                    .min().orElse(0);
            totalPrice -= cheapest;
            customer.resetLoyaltyPoints();
        }

        customerRepository.save(customer);
        return new PurchaseResponse(totalPrice, customer.getLoyaltyPoints());
    }
}
