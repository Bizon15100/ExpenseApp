package com.expenses;


import java.time.LocalDate;

import static com.expenses.Expense.*;

public class ExpenseApp {
    public static void main(String[] args) throws InvalidExpenseException {
        Builder expense1 = Expense.builder()
                .amount(3)
                .date(LocalDate.now())
                .place("Market")
                .category("Relax")
                .build();

        Builder expense2 = builder()
                .amount(3.22)
                .date(LocalDate.now())
                .place("Market")
                .category("Pizza")
                .build();

        Builder expense3 = builder()
                .amount(32.22)
                .date(LocalDate.now())
                .place("Market")
                .category("Pizza")
                .build();

        Builder expense4 = builder()
                .amount(55)
                .date(LocalDate.now())
                .place("Market")
                .category("Pizza")
                .build();

        Builder expense5 = builder()
                .amount(30.2)
                .date(LocalDate.now())
                .place("Market")
                .category("Pizza")
                .build();

        ExpenseService financesService = new ExpenseService();

        financesService.addExpense(expense1);

        financesService.addExpense(expense1);
        financesService.addExpense(expense2);
        financesService.addExpense(expense3);
        financesService.addExpense(expense4);
        financesService.addExpense(expense5);

        System.out.println(financesService.toString());


    }
}
