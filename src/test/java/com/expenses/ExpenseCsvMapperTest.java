package com.expenses;

import com.expenses.io.ExpenseCsvMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.expenses.Expense.builder;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseCsvMapperTest {


    @Test
    void shouldWriteExpenses() throws Exception{
        StringWriter stringWriter = new StringWriter();
        ExpenseCsvMapper expenseCsvMapper = new ExpenseCsvMapper();
        //Given
        Set<Expense> expenses = Set.of(
                Expense.builder().amount(valueOf(100))
                        .date(LocalDate.of(2011,11,11))
                        .place("Home")
                        .category("Pizza")
                        .build(),
                Expense.builder().amount(valueOf(150))
                        .date(LocalDate.of(2011,12,11))
                        .place("Home")
                        .category("Pizza")
                        .build());

        String expectedLine1 = "amount,date,place,category";
        String expectedLine2 = "100,11-11-2011,Home,Pizza";
        String expectedLine3 = "150,11-12-2011,Home,Pizza";


        //When
        expenseCsvMapper.write(expenses, stringWriter);

        //Then
        List<String> csvLines = Arrays.asList(stringWriter.toString().split("\n"));

        assertEquals(csvLines.size(),3);
        assertTrue(csvLines.contains(expectedLine1));
        assertTrue(csvLines.contains(expectedLine2));
        assertTrue(csvLines.contains(expectedLine3));

    }
    @Test
    void shouldReadExpenses() throws InvalidExpenseException, IOException {
        //Given
        ExpenseCsvMapper expenseCsvMapper = new ExpenseCsvMapper();
        String expensesCsv = "amount,date,place,category\n"
                + "100,11-11-2011,Home,Pizza\n"
                + "150,11-12-2011,Home,Pizza\n";


        Expense expense1 = builder().amount(valueOf(100))
                .date(LocalDate.of(2011, 11, 11))
                .place("Home")
                .category("Pizza")
                .build();
        Expense expense2 = builder().amount(valueOf(150))
                .date(LocalDate.of(2011, 12, 11))
                .place("Home")
                .category("Pizza")
                .build();

        //When
        Set<Expense> actualExpenses = expenseCsvMapper.read(new StringReader(expensesCsv));

        //Then
        assertEquals(2,actualExpenses.size());
        assertTrue(actualExpenses.contains(expense1), "\nSet: " + actualExpenses + "\nshould contain:\n" + expense1);
        assertTrue(actualExpenses.contains(expense2), "\nSet: " + actualExpenses + "\nshould contain:\n" + expense2);


    }
}