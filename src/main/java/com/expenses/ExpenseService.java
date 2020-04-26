package com.expenses;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ExpenseService {
   private Set<Expense> expenses = new HashSet<>();

    public void addExpense(Expense expense)  {
         expenses.add(expense);
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
