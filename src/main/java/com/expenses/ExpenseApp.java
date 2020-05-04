package com.expenses;

import java.time.LocalDate;

public class ExpenseApp {
    public static void main(String[] args) throws InvalidExpenseException {
        Expense expense1 = Expense.from(100.02, LocalDate.now().minusDays(5), "restaurant", "food");
        Expense expense2 = Expense.from(101, LocalDate.now(), "restaurant","food");
        Expense expense3 = Expense.from(102, LocalDate.now(), "restaurant","food");


        ExpenseService expenseService = new ExpenseService();
        expenseService.addExpense(expense1);
        expenseService.addExpense(expense2);
        expenseService.addExpense(expense3);
        System.out.println(expenseService);
    }
}
