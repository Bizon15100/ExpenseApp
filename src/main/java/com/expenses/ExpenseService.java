package com.expenses;

import java.time.LocalDate;
import java.util.*;

public class ExpenseService {
   private Set<Expense> expenses = new HashSet<>();

    public void addExpense(Expense expense)  {
         expenses.add(expense);
   }

    public Set<Expense> findExpensesInRange(LocalDate from, LocalDate to){
        Set<Expense> expensesInRange = new HashSet<>();
       for (Expense expense:expenses) {
           if ((expense.getDate().isAfter(from) | expense.getDate().isEqual(from)) && (expense.getDate().isBefore(to)|expense.getDate().isEqual(to))){
               expensesInRange.add(expense);
           }
       }
       return expensesInRange;
   }

    public List<Expense> getNLargestExpenses(int number){

        List<Expense> nLargestExpenses = new LinkedList<>();
        List<Expense> largestExpenses = new LinkedList<>();
        largestExpenses.addAll(expenses);
        largestExpenses.sort(Comparator.comparingDouble(Expense::getAmount));
        for (int i = largestExpenses.size()-1; i > number ; i--) {
            nLargestExpenses.add(largestExpenses.get(i));
        }
        return nLargestExpenses;


    }

   public Set<Expense> findExpensesByDate(LocalDate date){
        Set<Expense> expenseWithRequestedDate = new HashSet<>();

        for (Expense expense: expenses){
            if (expense.getDate().equals(date)){
                expenseWithRequestedDate.add(expense);
            }
        }
        return expenseWithRequestedDate;
   }

    public Set<Expense> getExpenseSet() {
        return new HashSet<>(expenses);
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder("Expenses: \n");

        for (Expense expense: expenses){
            message.append(expense).append("\n");
        }

        return message.toString();
    }
}
