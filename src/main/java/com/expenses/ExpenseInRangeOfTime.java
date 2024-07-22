package com.expenses;

import com.expenses.Object.Expense;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.function.Predicate;

public class ExpenseInRangeOfTime implements Predicate<Expense>  {

    private final LocalDate comparedDateFrom;
    private final LocalDate comparedDateTo;

    public ExpenseInRangeOfTime(LocalDate comparedDateFrom, LocalDate comparedDateTo) {
        this.comparedDateFrom = comparedDateFrom;
        this.comparedDateTo = comparedDateTo;
    }


    @Override
    public boolean test(Expense expense) {
        return !expense.getDate().isBefore(comparedDateFrom) & !expense.getDate().isAfter(comparedDateTo);
    }
}
