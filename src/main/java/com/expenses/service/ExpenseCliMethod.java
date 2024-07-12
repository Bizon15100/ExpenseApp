package com.expenses.service;

import com.expenses.InvalidExpenseException;
import com.expenses.Logger;
import com.expenses.Object.Expense;
import com.expenses.Object.ExpensePrev;
import com.expenses.io.ExpenseFileMapper;
import com.expenses.io.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static java.math.BigDecimal.valueOf;

@Service
public class ExpenseCliMethod {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Scanner scanner = new Scanner(System.in);

    @Autowired
    Logger logger;

    ExpenseService service = new ExpenseService();

    public void loadData(String fileName, FileType fileType) throws  InvalidExpenseException {
        System.out.println("Loading data from " + fileName);

        Set<Expense> expenses;
        try {
            ExpenseFileMapper expenseFileMapper = new ExpenseFileMapper();
            expenses = expenseFileMapper.readFromFile(fileName,fileType);
        } catch (IOException exception){
            System.out.println("Could not load data from " + fileName
                    + ": " + exception.getMessage());
            return;
        }
        ExpenseService expenseService = service;
        for (Expense expense : expenses) {
            ExpensePrev expenseRevert = ExpensePrev.expenseRevert(expense);
            expenseService.addExpense(expenseRevert);
        }
        System.out.println("Successfully loaded " + expenses);
    }
    public void writeData(String fileName, FileType fileType) {
        System.out.println("Saving data to " + fileName);

       Set<Expense> expenses = service.getExpenseSet();
        try {
            ExpenseFileMapper expenseFileMapper = new ExpenseFileMapper();
            expenseFileMapper.writeToFile(fileName,fileType,expenses);
        } catch (IOException exception) {
            System.out.println("Could not load data from " + fileName
                    + ": " + exception.getMessage());
            return;
        }

        System.out.println("Successfully saved " + expenses.size() + " expenses.");

    }
    public void load(String fileName) throws InvalidExpenseException, IOException {

            String[] split = fileName.split("\\.");

            if (split.length == 2) {
                String extension = split[1];
                if (extension.equals("csv")) {
                    loadData(fileName, FileType.CSV);
                } else if (extension.equals("json")) {
                    loadData(fileName, FileType.JSON);
                    File file = new File(fileName);
                    if (file.createNewFile()) {
                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write("[]");
                        System.out.println("New file created");
                    } else System.out.println(fileName + " exists.");

                } else System.out.println("Unknown extension");
            } else System.out.println("Invalid file name format");

    }
    public void save(String fileName){
        String[] split = fileName.split("\\.");

        if (split.length==2){
            String extension = split[1];
            if (extension.equals("csv")){
                writeData(fileName,FileType.CSV);
            }
            if (extension.equals("json")){
                writeData(fileName,FileType.JSON);
            }
        }

    }

}
