package com.expenses;


import com.expenses.io.VarType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.expenses.Expense.builder;
import static com.expenses.io.VarType.*;
import static java.math.BigDecimal.*;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseServiceTest {

    @Test
    void shouldReturnDatesInRangeOfDates() throws InvalidExpenseException {
        LocalDate now = LocalDate.now();
        Expense expense1 = builder()
                .amount(valueOf(100))
                .date(LocalDate.now().minusYears(1))
                .place("Market")
                .category("Relax")
                .build();

        Expense expense2 = builder()
                .amount(valueOf(100))
                .date(LocalDate.now().minusDays(13))
                .place("Market")
                .category("Pizza")
                .build();

        Expense expense3 = builder()
                .amount(valueOf(100))
                .date(LocalDate.now().minusWeeks(7))
                .place("Market")
                .category("Pizza")
                .build();

        Expense expense4 = builder()
                .amount(valueOf(100))
                .date(LocalDate.now().minusMonths(2))
                .place("Market")
                .category("Pizza")
                .build();

        Expense expense5 = builder()
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

        Set<Expense> expensesInRange = expenseService.findExpensesInRange(now.minusMonths(1), now);

        assertTrue(expensesInRange.contains(expense2));
        assertFalse(expensesInRange.contains(expense3));
    }

    @Test
    void shouldReturnNTheBiggestAmounts() throws InvalidExpenseException {
        Expense expense1 = builder()
                .amount(valueOf(100))
                .date(LocalDate.now().minusYears(1))
                .place("Market")
                .category("Relax")
                .build();

        Expense expense2 = builder()
                .amount(valueOf(20))
                .date(LocalDate.now().minusYears(1))
                .place("Market")
                .category("Relax")
                .build();

        Expense expense3 = builder()
                .amount(valueOf(10))
                .date(LocalDate.now().minusYears(1))
                .place("Market")
                .category("Relax")
                .build();

        Expense expense4 = builder()
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


        List<Expense> nLargestExpenses = expenseService.getNLargestExpenses(2);

        assertTrue(nLargestExpenses.contains(expense4));
        assertTrue(nLargestExpenses.contains(expense1));
        assertFalse(nLargestExpenses.contains(expense2));

    }


    @Test
    void shouldNotAllowNull() {
       // LocalDate dateAsNull = null;

        Executable createNullDate = () -> builder().amount(valueOf(100)).date(null).place("Market").category("Pizza").build();

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

        Expense expense = builder().amount(valueOf(amount)).date(LocalDate.now()).place("Market").category("Pizza").build();

        ExpenseService expenseService = new ExpenseService();
        expenseService.addExpense(expense);

        assertTrue(expenseService.getExpenseSet().contains(expense));
    }

    @Test
    void shouldReturnExpensesWithRequestedDate() throws InvalidExpenseException {
        LocalDate requestedDate = LocalDate.now().minusDays(5);
        Expense expense1 = builder()
                .amount(valueOf(100))
                .date(requestedDate)
                .place("Market")
                .category("Pizza")
                .build();
        Expense expense2 = builder()
                .amount(valueOf(100))
                .date(requestedDate.minusMonths(1))
                .place("Market")
                .category("Pizza")
                .build();
        Expense expense3 = builder()
                .amount(valueOf(120))
                .date(requestedDate)
                .place("Market")
                .category("Pizza")
                .build();

        ExpenseService expenseService = new ExpenseService();
        expenseService.addExpense(expense1);
        expenseService.addExpense(expense2);
        expenseService.addExpense(expense3);

        Set<Expense> foundExpenses = expenseService.findExpensesByDate(requestedDate);

        assertEquals(2, foundExpenses.size());
        assertTrue(foundExpenses.contains(expense1));
        assertTrue(foundExpenses.contains(expense3));

    }

    @Test
    void shouldReturnAverageOfAmounts() throws InvalidExpenseException {
        Expense expense1 = builder()
                .amount(valueOf(30.0))
                .date(LocalDate.now().minusWeeks(3))
                .place("Market")
                .category("Pizza")
                .build();
        Expense expense2 = builder()
                .amount(valueOf(20.0))
                .date(LocalDate.now().minusWeeks(2))
                .place("Market")
                .category("Pizza")
                .build();
        Expense expense3 = builder()
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
    void shouldReturnExpensesInOneCategory() throws InvalidExpenseException {
        Expense expense1 = builder()
                .amount(valueOf(10))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Relax")
                .build();
        Expense expense2 = builder()
                .amount(valueOf(22.1))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();
        Expense expense3 = builder()
                .amount(valueOf(32.22))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();
        Expense expense4 = builder()
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

        Set<Expense> categorizedExpenses = financesService.expensesInOneCategory("Pizza");

        assertTrue(categorizedExpenses.contains(expense2));
        assertTrue(categorizedExpenses.contains(expense3));
        assertTrue(categorizedExpenses.contains(expense4));
        assertFalse(categorizedExpenses.contains(expense1));
    }

    @Test
    void shouldReturnTheBiggestExpenseInGivenCategory() throws InvalidExpenseException {
        Expense expense1 = builder()
                .amount(valueOf(10))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Relax")
                .build();
        Expense expense2 = builder()
                .amount(valueOf(22.1))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();
        Expense expense3 = builder()
                .amount(valueOf(32.22))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();
        Expense expense4 = builder()
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
        Expense expense1 = builder()
                .amount(valueOf(10))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Relax")
                .build();
        Expense expense2 = builder()
                .amount(valueOf(22.1))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();
        Expense expense3 = builder()
                .amount(valueOf(32.22))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();
        Expense expense4 = builder()
                .amount(valueOf(55))
                .date(LocalDate.now().minusWeeks(1))
                .place("Market")
                .category("Pizza")
                .build();
        Expense expense5 = builder()
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
        Expense expense1 = builder()
                .amount(valueOf(10))
                .date(LocalDate.now())
                .place("Market")
                .category("Relax")
                .build();
        Expense expense2 = builder()
                .amount(valueOf(22.1))
                .date(LocalDate.now())
                .place("Market")
                .category("Pizza")
                .build();
        Expense expense3 = builder()
                .amount(valueOf(32.22))
                .date(LocalDate.now())
                .place("Market")
                .category("Pizza")
                .build();
        Expense expense4 = builder()
                .amount(valueOf(55))
                .date(LocalDate.now())
                .place("Market")
                .category("Pizza")
                .build();
        Expense expense5 = builder()
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

    @Test
    void shouldTestSortByObjectMethod() throws InvalidExpenseException {
        //given
        Expense expense1 = builder()
                .amount(valueOf(10.0))
                .date(LocalDate.now())
                .place("Market")
                .category("Relax")
                .build();
        Expense expense2 = builder()
                .amount(valueOf(22.0))
                .date(LocalDate.now())
                .place("Market")
                .category("Pizza")
                .build();
        Expense expense3 = builder()
                .amount(valueOf(320.0))
                .date(LocalDate.now())
                .place("Market")
                .category("Pizza")
                .build();
        Expense expense4 = builder()
                .amount(valueOf(30.0))
                .date(LocalDate.now().minusWeeks(2))
                .place("Darket")
                .category("Pizza")
                .build();
        Expense expense5 = builder()
                .amount(valueOf(511.0))
                .date(LocalDate.now())
                .place("House")
                .category("Relax")
                .build();

        ExpenseService service = new ExpenseService();
        service.addExpense(expense1);
        service.addExpense(expense2);
        service.addExpense(expense3);
        service.addExpense(expense4);
        service.addExpense(expense5);
        //when
        List<Expense> expectedList = service.getExpenseSet()
                .stream()
                .sorted(Comparator.comparing(Expense::getAmount))
                .collect(Collectors.toList());
        //then
        List<Expense> resultList = new ArrayList<>(service.sortByObject(AMOUNT, "asc"));

        assertEquals(expectedList.get(0), resultList.get(0));
        assertEquals(expectedList.get(1), resultList.get(1));
        assertEquals(expectedList.get(2), resultList.get(2));
        assertEquals(expectedList.get(3), resultList.get(3));
        assertEquals(expectedList.get(4), resultList.get(4));


    }


}