package com.expenses;

import java.time.LocalDate;
import java.util.function.Predicate;

import static com.expenses.Expense.*;

public class ExpenseInRangeOfTime implements Predicate<Builder> {

    private final LocalDate comparedDateFrom;
    private final LocalDate comparedDateTo;

    public ExpenseInRangeOfTime(LocalDate comparedDateFrom, LocalDate comparedDateTo) {
        this.comparedDateFrom = comparedDateFrom;
        this.comparedDateTo = comparedDateTo;
    }


    @Override
    public boolean test(Builder expense) {
        return !expense.getDate().isBefore(comparedDateFrom) & !expense.getDate().isAfter(comparedDateTo);
    }
}
