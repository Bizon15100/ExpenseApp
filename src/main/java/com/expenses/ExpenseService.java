package com.expenses;

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
        double average = 0d;
        for (Expense expense : expensesInRange) {
            average = average + expense.getAmount();
        }
        if (expensesInRange.size() != 0 | !expensesInRange.isEmpty()) {
            return average / expensesInRange.size() + average % expensesInRange.size();
        } else return 0d;
    }

    public double theBiggestExpenseInGivenCategory(String category) {
        Set<Expense> expensesInCategory = expensesInOneCategory(category);
        double result = 0d;
        new ArrayList<>(expensesInCategory).sort(Comparator.comparingDouble(Expense::getAmount));

        for (Expense expense : expensesInCategory) {
            List<Expense> expenseList = new ArrayList<>(expensesInCategory);
            result = expenseList.get(expenseList.size()-1).getAmount();
        }

        return result;
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
