package com.expenses;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.expenses.Expense.*;

public class ExpenseService {
    private Set<Builder> expenses = new HashSet<>();

    public void addExpense(Builder expense) {
        expenses.add(expense);
    }

    public Set<Builder> getExpenseSet() {
        return new HashSet<>(expenses);
    }

    public Set<Builder> findExpensesInRange(LocalDate from, LocalDate to) {
        Set<Builder> expensesInRange = new HashSet<>();
        for (Builder expense : expenses) {
            if (expense.getDate().isBefore(to) & expense.getDate().isAfter(from)){
                expensesInRange.add(expense);
            }
        }
        return expensesInRange;
    }

    public List<Builder> getNLargestExpenses(int number) {

        List<Builder> nLargestExpenses = new LinkedList<>();
        List<Builder> largestExpenses = new LinkedList<>(expenses);
        largestExpenses.sort(Comparator.comparing(Builder::getAmount));
        for (int i = largestExpenses.size() - 1; i >= number; i--) {
            nLargestExpenses.add(largestExpenses.get(i));
        }
        return nLargestExpenses;


    }

    public Set<Builder> findExpensesByDate(LocalDate date) {
        Set<Builder> expenseWithRequestedDate = new HashSet<>();

        for (Builder expense : expenses) {
            if (expense.getDate().equals(date)) {
                expenseWithRequestedDate.add(expense);
            }
        }
        return expenseWithRequestedDate;
    }

    public Set<Builder> expensesInOneCategory(String category) {
        Set<Builder> expensesInOneCategory = new HashSet<>();
        for (Builder expense : expenses) {
            if (expense.getCategory().equals(category)) {
                expensesInOneCategory.add(expense);
            }
        }
        return expensesInOneCategory;
    }

    public Double averageOfExpensesInRangeOfTime(LocalDate from, LocalDate to) {
        Set<Builder> expensesInRange = findExpensesInRange(from, to);
        double sum = 0d;
        for (Builder expense : expensesInRange) {
            sum = sum + expense.getAmount();
        }
        if (expensesInRange.size() != 0 | !expensesInRange.isEmpty()) {
            return sum / expensesInRange.size() + sum%expensesInRange.size();
        } else return 0d;
    }

    public Double theBiggestExpenseInGivenCategory(String category) {
        Set<Builder> expensesInCategory = expensesInOneCategory(category);
        double result;

        List<Builder> expenseList = expensesInCategory
                .stream()
                .sorted(Comparator.comparingDouble(Builder::getAmount))
                .collect(Collectors.toList());

        result = expenseList.get(expenseList.size() - 1).getAmount();

        return result;
    }

    public Map<String, Double> mapOfCategoryAndLargestExpense() {
        Map<String, Double> map = new HashMap<>();
        for (Builder expense : expenses) {
            if (!map.containsKey(expense.getCategory())) {
                map.put(expense.getCategory(), theBiggestExpenseInGivenCategory(expense.getCategory()));
            }
        }
        return map;
    }

    public Double averageOfExpensesInCategory(String category) {
        Set<Builder> expensesInOneCategory = expensesInOneCategory(category);
        double sum = 0d;
        double average;
        for (Builder expense : expensesInOneCategory) {
            sum = sum + expense.getAmount();
        }
        average = sum / expensesInOneCategory.size();
        return average;
    }

    public Map<String, Double> mapOfCategoryAndAverageOfExpenses() {
        Map<String, Double> map = new HashMap<>();

        for (Builder expense : expenses) {
            if (!map.containsKey(expense.getCategory())) {
                double value = averageOfExpensesInCategory(expense.getCategory());
                BigDecimal bigDecimal = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
                map.put(expense.getCategory(), bigDecimal.doubleValue());
            }
        }
        return map;
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder("Expenses: \n");

        for (Builder expense : expenses) {
             message.append("|Amount|: ").append(expense.getAmount()).append(" ")
                     .append("|Date|: ").append(expense.getDate()).append(" ")
                     .append("|Place|: ").append(expense.getPlace()).append(" ")
                     .append("|Category|: ") .append(expense.getCategory()).append(" ")
                    .append("\n");
        }

        return message.toString();
    }


}
