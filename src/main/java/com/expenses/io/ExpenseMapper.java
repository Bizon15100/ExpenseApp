package com.expenses.io;

import com.expenses.Expense;
import com.expenses.InvalidExpenseException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Set;

public interface ExpenseMapper {
    Set<Expense> read(Reader reader) throws IOException, InvalidExpenseException;
    void write(Set<Expense> expenses, Writer writer) throws IOException;
}
