package com.expenses;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.expenses.Expense.*;
import static java.math.BigDecimal.*;

@SuppressWarnings("BigDecimalMethodWithoutRoundingCalled")
public class ExpenseService {
    private Set<Expense> expenses = new HashSet<>();

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public Set<Expense> getExpenseSet() {
        return new HashSet<>(expenses);
    }

    public Set<Expense> findExpensesInRange(LocalDate from, LocalDate to) {
        ExpenseInRangeOfTime expenseInRangeOfTime = new ExpenseInRangeOfTime(from,to);
        Set<Expense> expensesInRange = expenses.stream()
                .filter(expenseInRangeOfTime)
                .collect(Collectors.toSet());       // praca domowa



     //   for (Expense expense : expenses) {
     //       if (!expense.getDate().isBefore(from) & !expense.getDate().isAfter(to)){
     //           expensesInRange.add(expense);
     //       }
     //   }
        return expensesInRange;
    }

    public List<Expense> getNLargestExpenses(int number) {

        List<Expense> nLargestExpenses = new LinkedList<>();
        List<Expense> largestExpenses = new LinkedList<>(expenses);
        largestExpenses.sort(Comparator.comparing(Expense::getAmount));
        int positionStop = largestExpenses.size() - number;
        for (int i = largestExpenses.size()-1; i >= positionStop; i--) {
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

    public BigDecimal averageOfExpensesInRangeOfTime(LocalDate from, LocalDate to) {
        Set<Expense> expensesInRange = findExpensesInRange(from, to);
        BigDecimal sum = ZERO;
        for (Expense expense : expensesInRange) {
            sum =  expense.getAmount().add(sum);
        }
        if (expensesInRange.size() != 0 | !expensesInRange.isEmpty()) {
            return sum.divide(valueOf(expensesInRange.size()));
                    //+ sum%expensesInRange.size();
        } else return ZERO;
    }

    public BigDecimal theBiggestExpenseInGivenCategory(String category) {
        Set<Expense> expensesInCategory = expensesInOneCategory(category);
        BigDecimal result = null;

        List<Expense> expenseList = expensesInCategory
                .stream()
                .sorted(Comparator.comparing(Expense::getAmount))
                .collect(Collectors.toList());

        result = expenseList.get(expenseList.size() - 1).getAmount();

        return result;
    }

    public Map<String, BigDecimal> mapOfCategoryAndLargestExpense() {
        Map<String, BigDecimal> map = new HashMap<>();
        for (Expense expense : expenses) {
            if (!map.containsKey(expense.getCategory())) {
                map.put(expense.getCategory(), theBiggestExpenseInGivenCategory(expense.getCategory()));
            }
        }
        return map;
    }

    public BigDecimal averageOfExpensesInCategory(String category) {
        Set<Expense> expensesInOneCategory = expensesInOneCategory(category);
        BigDecimal sum = ZERO;
        BigDecimal average;
        for (Expense expense : expensesInOneCategory) {
            sum = sum.add(expense.getAmount());
        }
        average = sum.divide(valueOf(expensesInOneCategory.size()));
        return average;
    }

    public Map<String, BigDecimal> mapOfCategoryAndAverageOfExpenses() {
        Map<String, BigDecimal> map = new HashMap<>();

        for (Expense expense : expenses) {
            if (!map.containsKey(expense.getCategory())) {
                BigDecimal value = averageOfExpensesInCategory(expense.getCategory());
                BigDecimal bigDecimal = value.setScale(2, RoundingMode.HALF_UP);
                map.put(expense.getCategory(), bigDecimal);
            }
        }
        return map;
    }

    public String toString() {
        StringBuilder message = new StringBuilder("Expenses:\n");

        for (Expense expense : expenses) {
            message.append(expense).append("\n");
        }

        return message.toString();
    }


}
