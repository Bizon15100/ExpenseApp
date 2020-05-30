package com.expenses.io;

import com.expenses.Expense;
import com.expenses.InvalidExpenseException;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SuppressWarnings("unchecked")
public class ExpenseJsonMapper implements ExpenseMapper {
    @Override
    public Set<Expense> read(Reader reader) {
        JSONParser parser = new JSONParser();
        Set<Expense> expenses = new HashSet<>();
        try {
          Object obj = parser.parse(reader);
          JSONArray expenseList = (JSONArray) obj;
          expenseList.forEach(exp -> {
              try {
                  Expense expense = parseExpensesObject((JSONObject) exp);
                  expenses.add(expense);
              } catch (InvalidExpenseException e) {
                  e.printStackTrace();
              }
          });
        } catch (ParseException | IOException e){
            e.printStackTrace();
        }
        return expenses;
    }

    @Override
    public void write(@NotNull Set<Expense> expenses, Writer writer) throws IOException {
        JSONObject object = new JSONObject();
        JSONObject jsonExpense = new JSONObject();
        for (Expense expense:expenses) {
            object.put("amount",expense.getAmount().toString());
            object.put("date",expense.getDate().toString());
            object.put("place",expense.getPlace());
            object.put("category", expense.getCategory());
            jsonExpense.put("expense",object);
        }
        JSONArray expenseJsonList = new JSONArray();
        expenseJsonList.add(jsonExpense);
        writer.write(expenseJsonList.toJSONString());
    }

    @NotNull
    private static Expense parseExpensesObject(JSONObject jsonArray) throws InvalidExpenseException {
        JSONObject expense = (JSONObject) jsonArray.get("expense");
        String amount1 = (String) expense.get("amount");
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amount1));

        LocalDate data = LocalDate.parse(expense.get("date").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String category = (String) expense.get("category");
        String place = (String) expense.get("place");

        return Expense.builder()
                .amount(amount)
                .date(data)
                .place(place)
                .category(category)
                .build();
    }

}
