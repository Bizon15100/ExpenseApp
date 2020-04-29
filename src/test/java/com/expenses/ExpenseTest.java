package com.expenses;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

    @Test
    void shouldReturnDatesInRangeOfDates() throws InvalidExpenseException{
        LocalDate now = LocalDate.now();
        Expense expense1 = Expense.from(100,now,"Loc","Cat");
        Expense expense2 = Expense.from(100,now.minusDays(10),"Loc","Cat");
        Expense expense3 = Expense.from(100,now.minusMonths(2),"Loc","Cat");
        Expense expense4 = Expense.from(100,now.minusDays(25),"Loc","Cat");
        Expense expense5 = Expense.from(100,now.minusWeeks(6),"Loc","Cat");

        ExpenseService expenseService = new ExpenseService();
        expenseService.addExpense(expense1);
        expenseService.addExpense(expense2);
        expenseService.addExpense(expense3);
        expenseService.addExpense(expense4);
        expenseService.addExpense(expense5);

        Set<Expense> expensesInRange = expenseService.findExpensesInRange(now.minusMonths(1), now);

        assertTrue(expensesInRange.contains(expense2));
        assertFalse(expensesInRange.contains(expense3));
    }

    @Test
    void shouldReturnNTheBiggestAmounts() throws InvalidExpenseException{
        Expense expense1 = Expense.from(3.1, LocalDate.now(), "House", "Cat");
        Expense expense2 = Expense.from(3.33, LocalDate.now(), "House", "Cat");
        Expense expense3 = Expense.from(31.01, LocalDate.now(), "House", "Cat");
        Expense expense4 = Expense.from(133, LocalDate.now(), "House", "Cat");
        Expense expense5 = Expense.from(23.2, LocalDate.now(), "House", "Cat");

        ExpenseService expenseService = new ExpenseService();
        expenseService.addExpense(expense1);
        expenseService.addExpense(expense2);
        expenseService.addExpense(expense3);
        expenseService.addExpense(expense4);
        expenseService.addExpense(expense5);

        List<Expense> nLargestExpenses = expenseService.getNLargestExpenses(2);

        assertTrue(nLargestExpenses.contains(expense4));
        assertTrue(nLargestExpenses.contains(expense3));
        assertFalse(nLargestExpenses.contains(expense2));

    }

    @Test
    void shouldNotAllowFutureExpenses(){
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
    void shouldNotAcceptInvelidFormatOfDate(){
        LocalDate date = LocalDate.of(2022,Month.JANUARY,11);

        Executable createExpenseInFuture = () -> Expense
                .from(100.1, date, "Location", "Category");

        assertThrows(InvalidExpenseException.class,createExpenseInFuture);
    }

    @ParameterizedTest
    @ValueSource(doubles = {3.001, 3.332, 3.0100009, 1000.233})
    void shouldAcceptInvalidFormatOfAmount( double amount) {
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