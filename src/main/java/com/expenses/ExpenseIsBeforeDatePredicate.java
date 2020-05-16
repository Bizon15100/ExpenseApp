package com.expenses;

import java.time.LocalDate;
import java.util.function.Predicate;

import static com.expenses.Expense.*;

public class ExpenseIsBeforeDatePredicate implements Predicate<Builder> {

    private final LocalDate comparedDate;

    public ExpenseIsBeforeDatePredicate(LocalDate comparedDate) {
        this.comparedDate = comparedDate;
    }


    @Override
    public boolean test(Builder expense) {
        return expense.getDate().isBefore(comparedDate);
    }
}
