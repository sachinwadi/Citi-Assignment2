package com.citi.assignment.service;

import com.citi.assignment.model.Transaction;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class TransactionAnalysisService {


    private static final List<Transaction> ALL_TRANSACTIONS = getAllTransactions();

    //All transactions for a given category - latest first
    public Map<String, List<Transaction>> getAllTransactionsByCategory() {

        var result = ALL_TRANSACTIONS
                .stream()
                .collect(Collectors.groupingBy(Transaction::getCategory));

        return result;

    }

    //Total outgoing per category
    public Map<String, Double> getOutgoingPerCategory() {
        Map<String, List<Transaction>> transactionsByCategory = getAllTransactionsByCategory();

        var result = transactionsByCategory
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, y -> y.getValue()
                        .stream()
                        .map(Transaction::getAmount)
                        .reduce(0.00, Double::sum)));

        return result;
    }

    //Monthly average spend in a given category
    public Map<String, Map<YearMonth, Double>> getMonthlyAverageSpendByCategory() {
        Map<String, List<Transaction>> transactionsByCategory = getAllTransactionsByCategory();

        var result = transactionsByCategory
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, y -> getMonthlyAverageSpendForGivenCategory(y.getValue())));

        return result;
    }

    //Highest spend in a given category, for a given year
    public Map<Integer, Map<String, Double>> getHighestSpentInCategoryForGivenYear() {
        Map<Integer, List<Transaction>> transactionsByYear = getTransactionsByYear();

        var result = transactionsByYear
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, y -> getSpentSummaryStatisticsInCategory(y.getValue())
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, a -> a.getValue().getMax()))));

        return result;
    }

    //Lowest spend in a given category, for a given year
    public Map<Integer, Map<String, Double>> getLowestSpentInCategoryForGivenYear() {
        Map<Integer, List<Transaction>> transactionsByYear = getTransactionsByYear();

        var result = transactionsByYear
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, y -> getSpentSummaryStatisticsInCategory(y.getValue())
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, a -> a.getValue().getMin()))));

        return result;
    }

    private Map<Integer, List<Transaction>> getTransactionsByYear() {
        Map<Integer, List<Transaction>> transactionsByYear = ALL_TRANSACTIONS
                .stream()
                .collect(Collectors.groupingBy(x -> x.getTransactionDate().getYear()));
        return transactionsByYear;
    }

    private Map<String, DoubleSummaryStatistics> getSpentSummaryStatisticsInCategory(List<Transaction> transactions) {
        var result = transactions
                .stream()
                .collect(Collectors.groupingBy(Transaction::getCategory))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, y -> y.getValue()
                        .stream()
                        .map(Transaction::getAmount)
                        .mapToDouble(Double::doubleValue)
                        .summaryStatistics()));

        return result;
    }

    private Map<YearMonth, Double> getMonthlyAverageSpendForGivenCategory(List<Transaction> transactions) {

        var result = transactions
                .stream()
                .collect(Collectors.groupingBy(z -> YearMonth.from(z.getTransactionDate())))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, y -> y.getValue()
                        .stream()
                        .map(Transaction::getAmount)
                        .mapToDouble(Double::doubleValue)
                        .summaryStatistics()
                        .getAverage()));

        return result;
    }

    private static List<Transaction> getAllTransactions() {
        List<Transaction> collectiveData = new ArrayList<>();

        List<Transaction> transactions2020 = List.of(
                new Transaction(LocalDate.of(2020, 11, 01), "Morrisons", "card", 10.40, "Groceries"),
                new Transaction(LocalDate.of(2020, 10, 28), "CYBG", "direct debit", 600.00, "MyMonthlyDD"),
                new Transaction(LocalDate.of(2020, 10, 28), "PureGym", "direct debit", 40.00, "MyMonthlyDD"),
                new Transaction(LocalDate.of(2020, 10, 01), "M&S", "card", 5.99, "Groceries"),
                new Transaction(LocalDate.of(2020, 9, 30), "McMillan", "internet", 10.00, "")
        );

        List<Transaction> transactions2021 = List.of(
                new Transaction(LocalDate.of(2021, 1, 1), "Morrisons", "card", 13.20, "plumber"),
                new Transaction(LocalDate.of(2021, 2, 28), "CYBG", "direct debit", 500.40, "HVAC"),
                new Transaction(LocalDate.of(2021, 2, 28), "PureGym", "direct debit", 50.00, "MyMonthlyDD"),
                new Transaction(LocalDate.of(2021, 3, 1), "M&S", "card", 7.45, "Groceries"),
                new Transaction(LocalDate.of(2021, 4, 30), "McMillan", "internet", 16.00, "plumber")
        );

        List<Transaction> transactions2022 = List.of(
                new Transaction(LocalDate.of(2022, 5, 1), "Morrisons", "card", 29.40, "Groceries"),
                new Transaction(LocalDate.of(2022, 5, 28), "CYBG", "direct debit", 75.00, "Groceries"),
                new Transaction(LocalDate.of(2022, 6, 28), "PureGym", "direct debit", 575.00, "rent"),
                new Transaction(LocalDate.of(2022, 8, 1), "M&S", "card", 12.89, "doctor"),
                new Transaction(LocalDate.of(2022, 9, 30), "McMillan", "internet", 76.80, "medicine")
        );

        List<Transaction> transactions2023 = List.of(
                new Transaction(LocalDate.of(2023, 5, 1), "Morrisons", "card", 45.40, "Groceries"),
                new Transaction(LocalDate.of(2023, 7, 28), "CYBG", "direct debit", 455.00, "rent"),
                new Transaction(LocalDate.of(2023, 7, 28), "PureGym", "direct debit", 56.00, "MyMonthlyDD"),
                new Transaction(LocalDate.of(2023, 8, 1), "M&S", "card", 9.80, "doctor"),
                new Transaction(LocalDate.of(2023, 10, 30), "McMillan", "internet", 17.70, "doctor")
        );

        Stream.of(transactions2020, transactions2021, transactions2022, transactions2023).forEach(collectiveData::addAll);

        return collectiveData
                .stream()
                .sorted(Comparator.comparing(Transaction::getTransactionDate).reversed())
                .collect(toList());
    }
}