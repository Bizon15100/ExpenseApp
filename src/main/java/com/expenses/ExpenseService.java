package com.expenses;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseService {
    private Set<Expense> expenses = new HashSet<>();

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public Set<Expense> getExpenseSet() {
        return new HashSet<>(expenses);
    }

    public Set<Expense> findExpensesInRange(LocalDate from, LocalDate to) {
        Set<Expense> expensesInRange = new HashSet<>();
        for (Expense expense : expenses) {
            if (!expense.getDate().isBefore(from) && !expense.getDate().isAfter(to)) {
                expensesInRange.add(expense);
            }
        }
        return expensesInRange;
    }

    public List<Expense> getNLargestExpenses(int number) {

        List<Expense> nLargestExpenses = new LinkedList<>();
        List<Expense> largestExpenses = new LinkedList<>(expenses);
        largestExpenses.sort(Comparator.comparingDouble(Expense::getAmount));
        for (int i = largestExpenses.size() - 1; i > number; i--) {
            nLargestExpenses.add(largestExpenses.get(i));
        }
        return nLargestExpenses;


    }

    public Set<Expense> findExpensesByDate(LocalDate date) {
        Set<Expense> expenseWithRequestedDate = new HashSet<>();

        for (Expense expense : expenses) {
            if (expense.getDate().equals(date)) {
                expenseWithRequestedDate.add(expense);
            }
        }
        return expenseWithRequestedDate;
    }

    public Set<Expense> expensesInOneCategory(String category) {
        Set<Expense> expensesInOneCategory = new HashSet<>();
        for (Expense expense : expenses) {
            if (expense.getCategory().equals(category)) {
                expensesInOneCategory.add(expense);
            }
        }
        return expensesInOneCategory;
    }

    public Double averageOfExpensesInRangeOfTime(LocalDate from, LocalDate to) {
        Set<Expense> expensesInRange = findExpensesInRange(from, to);
        double sum = 0d;
        for (Expense expense : expensesInRange) {
            sum = sum + expense.getAmount();
        }
        if (expensesInRange.size() != 0 | !expensesInRange.isEmpty()) {
            return sum / expensesInRange.size() + sum % expensesInRange.size();
        } else return 0d;
    }

    public double theBiggestExpenseInGivenCategory(String category) {
        Set<Expense> expensesInCategory = expensesInOneCategory(category);
        double result = 0;

        List<Expense> expenseList = expensesInCategory.stream().collect(Collectors.toList());
        expenseList.sort(Comparator.comparingDouble(Expense::getAmount));

        result = expenseList.get(expenseList.size() - 1).getAmount();

        return result;
    }

    public Map<String, Double> mapOfCategoryAndLargestExpense() {
        Map<String, Double> map = new HashMap<>();
        for (Expense expense : expenses) {
            if (!map.containsKey(expense.getCategory())) {
                map.put(expense.getCategory(), theBiggestExpenseInGivenCategory(expense.getCategory()));
            }
        }
        return map;
    }

    public double averageOfExpensesInCategory(String category) {
        Set<Expense> expensesInOneCategory = expensesInOneCategory(category);
        double sum = 0;
        double average;
        for (Expense expense : expensesInOneCategory) {
            sum = sum + expense.getAmount();
        }
        average = sum /expensesInOneCategory.size();
        return average;
    }

    public Map<String, Double> mapOfCategoryAndAverageOfExpenses() {
        Map<String, Double> map = new HashMap<>();

        for (Expense expense : expenses) {
            if (!map.containsKey(expense.getCategory())) {
                double value = averageOfExpensesInCategory(expense.getCategory());
                BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
                double newInput = bd.doubleValue();
                map.put(expense.getCategory(), newInput);
            }
        }
        return map;
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder("Expenses: \n");

        for (Expense expense : expenses) {
            message.append(expense).append("\n");
        }

        return message.toString();
    }


}
