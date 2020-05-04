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
        LocalDate dateInFuture = LocalDate.of(2020, Month.MAY,30);

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
    void shouldThrowErrorBecauseOfFutureDateOfExpense(){
        LocalDate date = LocalDate.now().plusYears(2);

        Executable createExpenseInFuture = () -> Expense
                .from(100.1, date, "Location", "Category");

        assertThrows(InvalidExpenseException.class,createExpenseInFuture);
    }

    @ParameterizedTest
    @ValueSource(doubles = {132.001, 31.332, 3.0100009, 1000.233})
    void shouldThrowErrorBecauseOfInvalidFormatOfAmount( double amount) {
    Executable create = () -> Expense.from(amount,LocalDate.now(),
            "loc","Cat");

    assertThrows(InvalidExpenseException.class, create);
    }

    @ParameterizedTest
    @ValueSource(doubles = {132.00, 31.32, 3.0, 100})
    void shouldAcceptProperFormatOfAmount( double amount) throws InvalidExpenseException {

        Expense expense = Expense.from(amount,LocalDate.now(),"Shop","Food");

         ExpenseService expenseService = new ExpenseService();
         expenseService.addExpense(expense);

         assertTrue(expenseService.getExpenseSet().contains(expense));
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

        Set<Expense> foundExpenses = expenseService.findExpensesByDate(requestedDate);

        assertEquals(2,foundExpenses.size());
        assertTrue(foundExpenses.contains(expense1));
        assertTrue(foundExpenses.contains(expense3));

    }
    @Test
    void shouldReturnAverageOfAmounts () throws InvalidExpenseException{
        Expense expense1 = Expense.from(10, LocalDate.now().minusWeeks(3),"Pizza","Relax");
        Expense expense2 = Expense.from(20, LocalDate.now().minusWeeks(2),"Pizza","Pizza");
        Expense expense3 = Expense.from(30, LocalDate.now().minusWeeks(1),"Pizza","Pizza");

        ExpenseService financesService = new ExpenseService();
        financesService.addExpense(expense1);
        financesService.addExpense(expense2);
        financesService.addExpense(expense3);

        Double average = financesService.averageOfAmountsInRangeOfTime(LocalDate.now().minusWeeks(4), LocalDate.now());
        Double result = 20.00d;

        assertEquals(average, result);
    }

    @Test
    void shouldReturnExpensesInOneCategory() throws InvalidExpenseException{
        Expense expense1 = Expense.from(3.22, LocalDate.now(),"Market","Relax");
        Expense expense2 = Expense.from(3.22, LocalDate.now(),"Market","Pizza");
        Expense expense3 = Expense.from(32.22, LocalDate.now(),"Market","Pizza");
        Expense expense4 = Expense.from(55, LocalDate.now(),"Market","Pizza");

        ExpenseService financesService = new ExpenseService();
        financesService.addExpense(expense1);
        financesService.addExpense(expense2);
        financesService.addExpense(expense3);
        financesService.addExpense(expense4);

        Set<Expense> categorizedExpenses = financesService.expensesInOneCategory("Pizza");

        assertTrue(categorizedExpenses.contains(expense2));
        assertTrue(categorizedExpenses.contains(expense3));
        assertTrue(categorizedExpenses.contains(expense4));
        assertFalse(categorizedExpenses.contains(expense1));

    }
}