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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ExpenseJsonMapper implements ExpenseMapper {
    @Override
    public Set<Expense> read(Reader reader) {
        JSONParser parser = new JSONParser();
        Set<Expense> expenses = new HashSet<>();

        try {
          Object obj = parser.parse(reader);
          JSONArray expenseList = (JSONArray) obj;
          Expense expense;
            for (Object exp:expenseList) {
                try {
                    expense = parseExpensesObject((JSONObject) exp);
                    expenses.add(expense);
                } catch (InvalidExpenseException e) {
                    e.printStackTrace();
                }

            }
        } catch (ParseException | IOException e){
            e.printStackTrace();
        }

        return expenses;
    }

    @Override
    public void write(Set<Expense> expenses, Writer writer) throws IOException {
        JSONObject object = new JSONObject();
        JSONObject jsonExpense = new JSONObject();
        JSONArray expenseJsonList= new JSONArray();
        LinkedList<Expense> expenseList = new LinkedList<>(expenses);
        writer.append("[");

        for (int i = 0; i < expenseList.size() -1; i++) {
                object.put("amount",expenseList.get(i).getAmount().toString());
                object.put("date",expenseList.get(i).getDate().toString());
                object.put("place",expenseList.get(i).getPlace());
                object.put("category", expenseList.get(i).getCategory());
                jsonExpense.put("expense",object);
                writer.write(jsonExpense.toJSONString());
                writer.append(",");
        }
        object.put("amount",expenseList.get(expenseList.size()-1).getAmount().toString());
        object.put("date",expenseList.get(expenseList.size()-1).getDate().toString());
        object.put("place",expenseList.get(expenseList.size()-1).getPlace());
        object.put("category", expenseList.get(expenseList.size()-1).getCategory());
        jsonExpense.put("expense",object);
        writer.write(jsonExpense.toJSONString());
        writer.append("]");
        //expenseJsonList.add(jsonExpense);
        //writer.write(expenseJsonList.toJSONString());

    }

    @NotNull
    private static Expense parseExpensesObject(JSONObject jsonObject) throws InvalidExpenseException {
        JSONObject expense = (JSONObject) jsonObject.get("expense");
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
