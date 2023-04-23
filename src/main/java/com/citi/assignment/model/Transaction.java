package com.citi.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Transaction {
    LocalDate transactionDate;
    String vendor;
    String type;
    Double amount;
    String category;

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionDate=" + transactionDate +
                ", vendor='" + vendor + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                '}';
    }
}