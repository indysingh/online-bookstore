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
public class PurchaseServiceImpl implements PurchaseService{

    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;

    @Override
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

        // Apply loyalty points as discount: 1 point = $1
        int points = customer.getLoyaltyPoints();
        double discount = Math.min(points, totalPrice);
        totalPrice -= discount;
        int pointsUsed = (int) discount;
        customer.setLoyaltyPoints(points - pointsUsed);

        // Earn 1 point per book purchased
        customer.addLoyaltyPoints(books.size());

        customerRepository.save(customer);
        return new PurchaseResponse(totalPrice, customer.getLoyaltyPoints());
    }
}
