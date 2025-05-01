package com.sportygroup.bookstore.customer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private int loyaltyPoints;

    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }

    public void resetLoyaltyPoints() {
        this.loyaltyPoints = 0;
    }
}
