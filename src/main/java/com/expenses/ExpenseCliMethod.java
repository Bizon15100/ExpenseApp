package com.expenses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static com.expenses.Expense.builder;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

public class ExpenseCliMethod {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Scanner scanner = new Scanner(System.in);

    ExpenseService service = new ExpenseService();

    public void Add(){
        try {
            System.out.println("Enter amount, 0.00");
            String amount = br.readLine();
            System.out.println("Enter data, required format dd-MM-yyyy");
            String dataEntry = br.readLine();
            System.out.println("Enter place of expense");
            String place = br.readLine();
            System.out.println("Enter category of expense");
            String category = br.readLine();
            LocalDate data;
            try {
                data = LocalDate.parse(dataEntry, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            } catch (DateTimeException exception) {
                System.out.println("Entered data " + dataEntry + " id invalid.");
                return;
            }

            Expense build = builder()
                    .amount(valueOf(Double.parseDouble(amount)))
                    .date(data)
                    .place(place)
                    .category(category)
                    .build();

            service.addExpense(build);
            System.out.println("Expense added to data");

        } catch (IllegalArgumentException | IOException | InvalidExpenseException e) {
            e.printStackTrace();
            System.out.println("Invalid argument");
        }
    }

    public void quickAdd(){

        System.out.println("Enter expense in one line, format 'amount,date dd-MM-yyyy,place,category' ");
        String[] expenseInString = new String[0];
        try {
            expenseInString = br.readLine().split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (expenseInString.length == 4) {
            BigDecimal amount = ZERO;
            try {
                amount = valueOf(Double.parseDouble(expenseInString[0]));
            } catch (IllegalArgumentException e){
                e.printStackTrace();
                System.out.println("Enter number in format 0.00");
                return;
            }
            LocalDate data;
            try {
                data = LocalDate.parse(expenseInString[1], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeException exception) {
                System.out.println("Entered data " + expenseInString[1] + " id invalid.");
                return;
            }
            String place = expenseInString[2];
            String category = expenseInString[3];
            Expense expense;
            try {
                expense = builder()
                        .amount(amount)
                        .date(data)
                        .place(place)
                        .category(category)
                        .build();
            } catch (InvalidExpenseException e) {
                e.printStackTrace();
                return;
            }
            service.addExpense(expense);
            System.out.println("Expense added to data");

        } else System.out.println("You should give 4 arguments, separated by comma");
    }

    public void ExpenseAll(){
        Set<Expense> expenseSet = service.getExpenseSet();
        if (!expenseSet.isEmpty()) {
            for (Expense record : expenseSet) {
                System.out.println("Amount: " + record.getAmount() +
                        " |Data: " + record.getDate() +
                        " |Place: " + record.getPlace() +
                        " |Category: " + record.getCategory());

            }
        } else {
            System.out.println("No expense found");
        }
    }
    public void FindCategory(){
        System.out.println("Enter category name");
        String category = null;
        try {
            category = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Set<Expense> citiesByName = service.expensesInOneCategory(category);
        if (!citiesByName.isEmpty()) {
            for (Expense record : citiesByName) {
                System.out.println("Amount: " + record.getAmount() +
                        " |Data: " + record.getDate() +
                        " |Place: " + record.getPlace() +
                        " |Category: " + record.getCategory());
            }
        } else System.out.println("Given expense not found");
    }
    public void FindRange(){
        System.out.println("Enter 'from' date date dd-MM-yyyy");
        String entryDataFrom = scanner.nextLine();
        LocalDate dataFrom;
        try {
            dataFrom = LocalDate.parse(entryDataFrom, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeException exception) {
            System.out.println("Entered data " + entryDataFrom + " id invalid.");
            return;
        }
        System.out.println("Enter 'to' date dd-MM-yyyy");
        String entryDataTo = scanner.nextLine();
        LocalDate dataTo;
        try {
            dataTo = LocalDate.parse(entryDataTo, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeException exception) {
            System.out.println("Entered data " + entryDataTo + " id invalid.");
            return;
        }
        Set<Expense> citiesByPostalCode = service.findExpensesInRange(dataFrom, dataTo);
        if (!citiesByPostalCode.isEmpty()) {
            for (Expense record : citiesByPostalCode) {
                System.out.println("Amount: " + record.getAmount() +
                        " |Data: " + record.getDate() +
                        " |Place: " + record.getPlace() +
                        " |Category: " + record.getCategory());
            }
        } else System.out.println("Given postal code not found");
    }

    public void NLargest(){
        System.out.println("Enter number of the largest expenses");
        int n = scanner.nextInt();
        try {
            List<Expense> nLargestExpenses = service.getNLargestExpenses(n);
            if (nLargestExpenses.size() + 1 < n) {
                System.out.println("You have less expenses than you are looking for ;)");
            }
            for (Expense record : nLargestExpenses) {
                System.out.println(" Amount: " + record.getAmount() +
                        " |Data: " + record.getDate() +
                        " |Place: " + record.getPlace() +
                        " |Category: " + record.getCategory());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public void AverageFromTo(){
        System.out.println("Enter 'from' date date dd-MM-yyyy");
        String entryDataFrom = scanner.nextLine();
        LocalDate dataFrom;
        try {
            dataFrom = LocalDate.parse(entryDataFrom, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeException exception) {
            System.out.println("Entered data " + entryDataFrom + " id invalid.");
            return;
        }
        System.out.println("Enter 'to' date dd-MM-yyyy");
        String entryDataTo = scanner.nextLine();
        LocalDate dataTo;
        try {
            dataTo = LocalDate.parse(entryDataTo, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeException exception) {
            System.out.println("Entered data " + entryDataTo + " id invalid.");
            return;
        }
        BigDecimal bigDecimal = service.averageOfExpensesInRangeOfTime(dataFrom, dataTo);
        System.out.println("Average of yours expenses in given time: " + bigDecimal);
    }


}
