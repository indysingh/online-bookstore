package com.sportygroup.bookstore.service;

import com.sportygroup.bookstore.purchase.dto.PurchaseResponse;

import java.util.List;

public interface PurchaseService {
    PurchaseResponse purchaseBooks(List<Long> bookIds, Long customerId);
}
