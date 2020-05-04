package com.expenses;

import java.time.LocalDate;
import java.util.Map;

public class ExpenseApp {
    public static void main(String[] args) throws InvalidExpenseException {
        Expense expense1 = Expense.from(3.22, LocalDate.now(),"Market","Relax");
        Expense expense2 = Expense.from(3.22, LocalDate.now(),"Market","Pizza");
        Expense expense3 = Expense.from(32.22, LocalDate.now(),"Market","Pizza");
        Expense expense4 = Expense.from(55, LocalDate.now(),"Market","Pizza");
        Expense expense5 = Expense.from(30.2, LocalDate.now(),"Market","Relax");

        ExpenseService financesService = new ExpenseService();
        financesService.addExpense(expense1);
        financesService.addExpense(expense2);
        financesService.addExpense(expense3);
        financesService.addExpense(expense4);
        financesService.addExpense(expense5);

        Map<String, Double> map = financesService.mapOfCategoryAndExpenses();
        System.out.println(map.get("Pizza"));
    }
}
