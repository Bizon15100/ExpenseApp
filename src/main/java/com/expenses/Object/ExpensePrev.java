package com.expenses.Object;

import com.expenses.InvalidExpenseException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpensePrev {

    private String amount;
    private String date;
    private String place;
    private String category;


    public static Expense expensePrev(ExpensePrev expense) throws InvalidExpenseException {
        return Expense.builder()
                .amount(BigDecimal.valueOf(Double.parseDouble(expense.getAmount())))
                .date(LocalDate.parse(expense.date))
                .place(expense.place)
                .category(expense.category)
                .build();
    }
    public static ExpensePrev expenseRevert(Expense expense) {
        return ExpensePrev.builder()
                .amount(expense.getAmount().toString())
                .date(expense.getDate().toString())
                .place(expense.getPlace())
                .category(expense.getCategory())
                .build();

    }
}
