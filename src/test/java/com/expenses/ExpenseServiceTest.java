package com.expenses;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.expenses.Expense.*;
import static com.expenses.Expense.builder;
import static java.math.BigDecimal.*;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseServiceTest {

    @Test
    void shouldReturnDatesInRangeOfDates() throws InvalidExpenseException {
        LocalDate now = LocalDate.now();
        Builder expense1 = builder()
                .amount(valueOf(100))
                .date(LocalDate.now().minusYears(1))
                .place("Market")
                .category("Relax")
                .build();

        Builder expense2 = builder()
                .amount(valueOf(100))
                .date(LocalDate.now().minusDays(13))
                .place("Market")
                .category("Pizza")
                .build();

        Builder expense3 = builder()
                .amount(valueOf(100))
                .date(LocalDate.now().minusWeeks(7))
                .place("Market")
                .category("Pizza")
                .build();

        Builder expense4 = builder()
                .amount(valueOf(100))
                .date(LocalDate.now().minusMonths(2))
                .place("Market")
                .category("Pizza")
                .build();

        Builder expense5 = builder()
                .amount(valueOf(100))
                .date(LocalDate.now())
                .place("Market")
                .category("Pizza")
                .build();

        ExpenseService expenseService = new ExpenseService();
        expenseService.addExpense(expense1);
        expenseService.addExpense(expense2);
        expenseService.addExpense(expense3);
        expenseService.addExpense(expense4);
        expenseService.addExpense(expense5);

        Set<Builder> expensesInRange = expenseService.findExpensesInRange(now.minusMonths(1), now);

        assertTrue(expensesInRange.contains(expense2));
        assertFalse(expensesInRange.contains(expense3));
    }

    @Test
    void shouldReturnNTheBiggestAmounts() throws InvalidExpenseException {
        Builder expense1 = builder()
                .amount(valueOf(100))
                .date(LocalDate.now().minusYears(1))
                .place("Market")
                .category("Relax")
                .build();

        Builder expense2 = builder()
                .amount(valueOf(20))
                .date(LocalDate.now().minusYears(1))
                .place("Market")
                .category("Relax")
                .build();

        Builder expense3 = builder()
                .amount(valueOf(10))
                .date(LocalDate.now().minusYears(1))
                .place("Market")
                .category("Relax")
                .build();

        Builder expense4 = builder()
                .amount(valueOf(88.13))
                .date(LocalDate.now().minusYears(1))
                .place("Market")
                .category("Relax")
                .build();

        ExpenseService expenseService = new ExpenseService();
        expenseService.addExpense(expense1);
        expenseService.addExpense(expense2);
        expenseService.addExpense(expense3);
        expenseService.addExpense(expense4);


        List<Builder> nLargestExpenses = expenseService.getNLargestExpenses(2);

        assertTrue(nLargestExpenses.contains(expense4));
        assertTrue(nLargestExpenses.contains(expense1));
        assertFalse(nLargestExpenses.contains(expense2));

    }


    @Test
    void shouldNotAllowNull() {
        LocalDate dateAsNull = null;

        Executable createNullDate = () -> builder().amount(valueOf(100)).date(dateAsNull).place("Market").category("Pizza").build();

        assertThrows(InvalidExpenseException.class, createNullDate);
    }

    @Test
    void shouldThrowErrorBecauseOfFutureDateOfExpense() {
        LocalDate date = LocalDate.now().plusYears(2);

        Executable createExpenseInFuture = () -> builder().amount(valueOf(100)).date(date).place("Market").category("Pizza").build();

        assertThrows(InvalidExpenseException.class, createExpenseInFuture);
    }

    @ParameterizedTest
    @ValueSource(doubles = {132.001, 31.332, 3.0100009, 1000.233})
    void shouldThrowErrorBecauseOfInvalidFormatOfAmount(double amount) {
        Executable create = () -> builder().amount(valueOf(amount)).date(LocalDate.now()).place("Market").category("Pizza").build();

        assertThrows(InvalidExpenseException.class, create);

    }

    @ParameterizedTest
    @ValueSource(doubles = {132.00, 31.32, 3.0, 100, 22., .22})
    void shouldAcceptProperFormatOfAmount(double amount) throws InvalidExpenseException {

        Builder expense = builder().amount(valueOf(amount)).date(LocalDate.now()).place("Market").category("Pizza").build();

        ExpenseService expenseService = new ExpenseService();
        expenseService.addExpense(expense);

        assertTrue(expenseService.getExpenseSet().contains(expense));
    }

    @Test
    void shouldReturnExpensesWithRequestedDate() throws InvalidExpenseException {
        LocalDate requestedDate = LocalDate.now().minusDays(5);
        Builder expense1 = builder()
                .amount(valueOf(100))
                .date(requestedDate)
                .place("Market")
                .category("Pizza")
                .build();
        Builder expense2 = builder()
                .amount(valueOf(100))
                .date(requestedDate.minusMonths(1))
                .place("Market")
                .category("Pizza")
                .build();
        Builder expense3 = builder()
                .amount(valueOf(100))
                .date(requestedDate)
                .place("Market")
                .category("Pizza")
                .build();

        ExpenseService expenseService = new ExpenseService();
        expenseService.addExpense(expense1);
        expenseService.addExpense(expense2);
        expenseService.addExpense(expense3);

        Set<Builder> foundExpenses = expenseService.findExpensesByDate(requestedDate);

        assertEquals(2, foundExpenses.size());
        assertTrue(foundExpenses.contains(expense1));
        assertTrue(foundExpenses.contains(expense3));

    }

    @Test
    void shouldReturnAverageOfAmounts() throws InvalidExpenseException {
        Builder expense1 = builder()
                .amount(valueOf(30.0))
                .date(LocalDate.now().minusWeeks(3))
                .place("Market")
                .category("Pizza")
                .build();
        Builder expense2 = builder()
                .amount(valueOf(20.0))
                .date(LocalDate.now().minusWeeks(2))
                .place("Market")
                .category("Pizza")
                .build();
        Builder expense3 = builder()
                .amount(valueOf(10.0))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();

        ExpenseService financesService = new ExpenseService();
        financesService.addExpense(expense1);
        financesService.addExpense(expense2);
        financesService.addExpense(expense3);

        BigDecimal average = financesService.averageOfExpensesInRangeOfTime(LocalDate.now().minusWeeks(4), LocalDate.now());
        BigDecimal result = valueOf(20.00);

        assertEquals(average, result);
    }

    @Test
    void byleco() throws InvalidExpenseException {
        Builder expense1 = builder()
                .amount(valueOf(30.0))
                .date(LocalDate.now().minusWeeks(3))
                .place("Market")
                .category("Pizza")
                .build();
        Builder expense2 = builder()
                .amount(valueOf(20.0))
                .date(LocalDate.now().minusWeeks(2))
                .place("Market")
                .category("Pizza")
                .build();
        Builder expense3 = builder()
                .amount(valueOf(10.0))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();

        ExpenseService service = new ExpenseService();
        service.addExpense(expense1);
        service.addExpense(expense2);
        service.addExpense(expense3);

        List<Builder> nLargestExpenses = service.getNLargestExpenses(3);
        for (Builder record : nLargestExpenses) {
            System.out.println("Amount: " + record.getAmount() +
                    " |Data: " + record.getDate() +
                    " |Place: " + record.getPlace() +
                    " |Category: " + record.getCategory());
        }
    }

    @Test
    void shouldReturnExpensesInOneCategory() throws InvalidExpenseException {
        Builder expense1 = builder()
                .amount(valueOf(10))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Relax")
                .build();
        Builder expense2 = builder()
                .amount(valueOf(22.1))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();
        Builder expense3 = builder()
                .amount(valueOf(32.22))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();
        Builder expense4 = builder()
                .amount(valueOf(55))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();

        ExpenseService financesService = new ExpenseService();
        financesService.addExpense(expense1);
        financesService.addExpense(expense2);
        financesService.addExpense(expense3);
        financesService.addExpense(expense4);

        Set<Builder> categorizedExpenses = financesService.expensesInOneCategory("Pizza");

        assertTrue(categorizedExpenses.contains(expense2));
        assertTrue(categorizedExpenses.contains(expense3));
        assertTrue(categorizedExpenses.contains(expense4));
        assertFalse(categorizedExpenses.contains(expense1));
    }

    @Test
    void shouldReturnTheBiggestExpenseInGivenCategory() throws InvalidExpenseException {
        Builder expense1 = builder()
                .amount(valueOf(10))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Relax")
                .build();
        Builder expense2 = builder()
                .amount(valueOf(22.1))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();
        Builder expense3 = builder()
                .amount(valueOf(32.22))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();
        Builder expense4 = builder()
                .amount(valueOf(55))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();

        ExpenseService financesService = new ExpenseService();
        financesService.addExpense(expense1);
        financesService.addExpense(expense2);
        financesService.addExpense(expense3);
        financesService.addExpense(expense4);

        BigDecimal biggestExpense = financesService.theBiggestExpenseInGivenCategory("Pizza");

        assertEquals(biggestExpense, valueOf(55));

    }

    @Test
    void shouldReturnMapWithCategoryAndTheBiggestExpenseInThatCategory() throws InvalidExpenseException {
        Builder expense1 = builder()
                .amount(valueOf(10))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Relax")
                .build();
        Builder expense2 = builder()
                .amount(valueOf(22.1))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();
        Builder expense3 = builder()
                .amount(valueOf(32.22))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();
        Builder expense4 = builder()
                .amount(valueOf(55))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();
        Builder expense5 = builder()
                .amount(valueOf(30.2))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Relax")
                .build();

        ExpenseService financesService = new ExpenseService();
        financesService.addExpense(expense1);
        financesService.addExpense(expense2);
        financesService.addExpense(expense3);
        financesService.addExpense(expense4);
        financesService.addExpense(expense5);

        Map<String, BigDecimal> map = financesService.mapOfCategoryAndLargestExpense();

        assertTrue(map.containsValue(valueOf(30.2)));
        assertTrue(map.containsValue(valueOf(55)));
    }

    @Test
    void shouldReturnMapWithCategoryAndTheAverageOfExpensesInThatCategory() throws InvalidExpenseException {
        Builder expense1 = builder()
                .amount(valueOf(10))
                .date(LocalDate.now())
                .place("Market")
                .category("Relax")
                .build();
        Builder expense2 = builder()
                .amount(valueOf(22.1))
                .date(LocalDate.now())
                .place("Market")
                .category("Pizza")
                .build();
        Builder expense3 = builder()
                .amount(valueOf(32.22))
                .date(LocalDate.now())
                .place("Market")
                .category("Pizza")
                .build();
        Builder expense4 = builder()
                .amount(valueOf(55))
                .date(LocalDate.now())
                .place("Market")
                .category("Pizza")
                .build();
        Builder expense5 = builder()
                .amount(valueOf(30.2))
                .date(LocalDate.now())
                .place("Market")
                .category("Relax")
                .build();

        ExpenseService financesService = new ExpenseService();
        financesService.addExpense(expense1);
        financesService.addExpense(expense2);
        financesService.addExpense(expense3);
        financesService.addExpense(expense4);
        financesService.addExpense(expense5);

        Map<String, BigDecimal> map = financesService.mapOfCategoryAndAverageOfExpenses();


        assertTrue(map.containsValue(valueOf(36.44)));
        assertTrue(map.containsValue(valueOf(20.10).setScale(2, RoundingMode.HALF_UP)));
        assertEquals(2, map.size());


    }
}