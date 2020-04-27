package com.expenses;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

    @Test
    void shouldNotAllowExpensesFuture(){
        LocalDate dateInFuture = LocalDate.of(2020, Month.APRIL,30);

        Executable createExpenseInFuture = () -> Expense
                .from(100, dateInFuture, "Location", "Category");

        assertThrows(InvalidExpenseException.class,createExpenseInFuture);
    }

    @Test
    void shouldNotAllowNull(){
        LocalDate dateAsNull = null;

        Executable createExpenseInFuture = () -> Expense
                .from(100, dateAsNull, "Location", "Category");

        assertThrows(InvalidExpenseException.class,createExpenseInFuture);
    }
    @Test
    void shouldNotAcceptInvelidFormatOfAmount(){
        LocalDate date = LocalDate.of(2019,Month.JANUARY,11);

        Executable createExpenseInFuture = () -> Expense
                .from(100.1, date, "Location", "Category");

        assertThrows(InvalidExpenseException.class,createExpenseInFuture);
    }

    @ParameterizedTest
    @ValueSource(doubles = {3.001, 3.332, 3.0100009, 3.020, 3})
    void shouldNotAcceptInvalidFormatOfAmount( double amount) {
    Executable create = () -> Expense.from(amount,LocalDate.now(),
            "loc","Cat");

    assertThrows(InvalidExpenseException.class, create);
    }

    @Test
    void shouldAcceptProperFormatOfAmount() throws InvalidExpenseException {
        double amount1 = 2.3;
        double amount2 = 32.333;
        double amount3 = 2.32;

        LocalDate requestedDate = LocalDate.now().minusDays(5);
        Expense expense1 = Expense.from(2.3,requestedDate,"Loc","Cat");
        //Expense expense2 = Expense.from(32.333,requestedDate,"Loc","Cat");
        Expense expense3 = Expense.from(2.32,requestedDate,"Loc","Cat");

        Executable executable = () -> Expense
                .from(32.333, requestedDate, "Location", "Category");

        ExpenseService expenseService = new ExpenseService();
        expenseService.addExpense(expense1);
       // expenseService.addExpense(expense2);
        expenseService.addExpense(expense3);

        Set<Expense> foundExpences = expenseService.findExpensesByDate(requestedDate);
        assertThrows(InvalidExpenseException.class,  executable);
        assertTrue(foundExpences.contains(expense1));
        assertTrue(foundExpences.contains(expense3));

    }

    @Test
    void shouldReturnExpensesWIthRequestedDate() throws InvalidExpenseException {
        LocalDate requestedDate = LocalDate.now().minusDays(5);
        Expense expense1 = Expense.from(100,requestedDate,"Loc","Cat");
        Expense expense2 = Expense.from(100,requestedDate.minusDays(10),"Loc","Cat");
        Expense expense3 = Expense.from(100,requestedDate,"Loc","Cat");

        ExpenseService expenseService = new ExpenseService();
        expenseService.addExpense(expense1);
        expenseService.addExpense(expense2);
        expenseService.addExpense(expense3);

        Set<Expense> foundExpences = expenseService.findExpensesByDate(requestedDate);

        assertEquals(2,foundExpences.size());
        assertTrue(foundExpences.contains(expense1));
        assertTrue(foundExpences.contains(expense3));

    }

}