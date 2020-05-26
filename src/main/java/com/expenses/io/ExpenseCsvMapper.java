package com.expenses.io;

import com.expenses.Expense;
import com.expenses.InvalidExpenseException;
import com.expenses.io.ExpenseMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

public class ExpenseCsvMapper implements ExpenseMapper {

    private static final String DELIMITER = ",";

    private static final DateTimeFormatter DATA_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public Set<Expense> read(Reader reader)
            throws IOException, InvalidExpenseException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        Set<Expense> expenses = new HashSet<>();
        bufferedReader.readLine();

        String line = bufferedReader.readLine();

        while (line != null){
            expenses.add(mapFromCsvRow(line));
            line = bufferedReader.readLine();
        }

        return expenses;
    }

    @Override
    public void write(Set<Expense> expenses, Writer writer)
            throws IOException {
        writer.write("amount,date,place,category\n");
        for (Expense expense : expenses) {
            writer.write(mapToCsvRaw(expense));
        }

    }

    private static String mapToCsvRaw(Expense expense) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(expense.getAmount()).append(DELIMITER);
        stringBuilder.append(DATA_FORMATTER.format(expense.getDate())).append(DELIMITER);
        stringBuilder.append(expense.getPlace()).append(DELIMITER);
        stringBuilder.append(expense.getCategory());
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    public static Expense mapFromCsvRow(String row) throws IOException, InvalidExpenseException {
        String[] columns = row.split(DELIMITER);

        if (columns.length < 4) {
            throw new IOException("Row has to few columns:[" + row + "]");
        }

        String amountText = columns[0];
        BigDecimal amount;
        try {
           amount = new BigDecimal(amountText);
        } catch (NumberFormatException exception){
            throw new IOException("Cannot parse number in row: [" + amountText + "]", exception);
        }

        String dateText = columns[1];
        LocalDate date;
        try {
            date = LocalDate.parse(dateText, DATA_FORMATTER);
        } catch (DateTimeParseException exception){
            throw new IOException("Cannot parse date in row: [" + dateText + "]", exception);
        }

        String place = columns[2];

        String category = columns[3];

        return Expense.builder()
                .amount(amount)
                .date(date)
                .place(place)
                .category(category)
                .build();
    }
}
