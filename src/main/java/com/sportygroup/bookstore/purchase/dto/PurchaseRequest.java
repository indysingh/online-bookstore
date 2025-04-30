package com.sportygroup.bookstore.purchase.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PurchaseRequest(
        @NotEmpty(message = "Book IDs must not be empty")
        List<Long> bookIds,

        @NotNull(message = "Customer ID must not be null")
        Long customerId
) {}
