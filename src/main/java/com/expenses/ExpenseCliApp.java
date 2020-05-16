package com.expenses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static com.expenses.Expense.Builder;
import static com.expenses.Expense.builder;
import static java.math.BigDecimal.*;

public class ExpenseCliApp {

    public static void main(String[] args) throws InvalidExpenseException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Scanner scanner = new Scanner(System.in);

        ExpenseService service = new ExpenseService();
        System.out.println("Welcome in my app.");
        System.out.println("Menu: ");
        System.out.println("Add expense -> insert add");
        System.out.println("Quick add of expense -> insert quickAdd");
        System.out.println("Print all added expenses -> insert expenseAll");
        System.out.println("Find expense by given category -> insert findCategory");
        System.out.println("Find expenses by given range of dates -> insert  findRange");
        System.out.println("Find given number of the largest expenses -> insert nLargest");
        System.out.println("Print the average of expenses in given range of time -> insert averageFromTo");
        System.out.println("To print average of expenses in given category -> insert averageInCategory");
        System.out.println("To print a map of all categories and its averages -> insert mapAverage");
        System.out.println("to print a map of all categories and its largest expense -> insert mapLargest");
        System.out.println("Exit the app -> insert x");
        while (true) {
            System.out.println("Insert command");
            String command = br.readLine();
            switch (command) {
                case "add": {
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

                        Builder build = builder()
                                .amount(valueOf(Double.parseDouble(amount)))
                                .date(data)
                                .place(place)
                                .category(category)
                                .build();

                        service.addExpense(build);
                        System.out.println("Expense added to data");

                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        System.out.println("Invalid argument");
                        break;
                    }
                }
                case "quickAdd": {

                    System.out.println("Enter expense in one line, format 'amount,date dd-MM-yyyy,place,category' ");
                    String[] expenseInString = br.readLine().split(",");
                    if (expenseInString.length == 4) {
                        BigDecimal amount = ZERO;
                        try {
                            amount = valueOf(Double.parseDouble(expenseInString[0]));
                        } catch (IllegalArgumentException e){
                            e.printStackTrace();
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
                        Builder expense = builder()
                                .amount(amount)
                                .date(data)
                                .place(place)
                                .category(category)
                                .build();
                        service.addExpense(expense);
                        System.out.println("Expense added to data");
                        break;
                    } else System.out.println("You should give 4 arguments, separated by comma");
                    break;
                }
                case "expenseAll": {
                    Set<Builder> expenseSet = service.getExpenseSet();
                    if (!expenseSet.isEmpty()) {
                        for (Builder record : expenseSet) {
                            System.out.println("Amount: " + record.getAmount() +
                                    " |Data: " + record.getDate() +
                                    " |Place: " + record.getPlace() +
                                    " |Category: " + record.getCategory());

                        }
                    } else {
                        System.out.println("No expense found");
                    }
                    break;
                }

                case "findCategory": {
                    System.out.println("Enter category name");
                    String category = br.readLine();
                    Set<Builder> citiesByName = service.expensesInOneCategory(category);
                    if (!citiesByName.isEmpty()) {
                        for (Builder record : citiesByName) {
                            System.out.println("Amount: " + record.getAmount() +
                                    " |Data: " + record.getDate() +
                                    " |Place: " + record.getPlace() +
                                    " |Category: " + record.getCategory());
                        }
                    } else System.out.println("Given expense not found");
                    break;
                }
                case "findRange": {
                    System.out.println("Enter 'from' date date dd-MM-yyyy");
                    String entryDataFrom = scanner.nextLine();
                    LocalDate dataFrom;
                    try {
                        dataFrom = LocalDate.parse(entryDataFrom, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    } catch (DateTimeException exception) {
                        System.out.println("Entered data " + entryDataFrom + " id invalid.");
                        break;
                    }
                    System.out.println("Enter 'to' date dd-MM-yyyy");
                    String entryDataTo = scanner.nextLine();
                    LocalDate dataTo = null;
                    try {
                        dataTo = LocalDate.parse(entryDataTo, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    } catch (DateTimeException exception) {
                        System.out.println("Entered data " + entryDataTo + " id invalid.");
                    }
                    Set<Builder> citiesByPostalCode = service.findExpensesInRange(dataFrom, dataTo);
                    if (!citiesByPostalCode.isEmpty()) {
                        for (Builder record : citiesByPostalCode) {
                            System.out.println("Amount: " + record.getAmount() +
                                    " |Data: " + record.getDate() +
                                    " |Place: " + record.getPlace() +
                                    " |Category: " + record.getCategory());
                        }
                        break;
                    } else System.out.println("Given postal code not found");
                    break;
                }
                case "nLargest": {
                    System.out.println("Enter number of the largest expenses");
                    int n = scanner.nextInt();
                    try {
                        List<Builder> nLargestExpenses = service.getNLargestExpenses(n);
                        if (nLargestExpenses.size() + 1 < n) {
                            System.out.println("You have less expenses than you are looking for ;)");
                            break;
                        }
                        for (Builder record : nLargestExpenses) {
                            System.out.println(" Amount: " + record.getAmount() +
                                    " |Data: " + record.getDate() +
                                    " |Place: " + record.getPlace() +
                                    " |Category: " + record.getCategory());
                        }
                        break;
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
                case "averageFromTo": {
                    System.out.println("Enter 'from' date date dd-MM-yyyy");
                    String entryDataFrom = scanner.nextLine();
                    LocalDate dataFrom;
                    try {
                        dataFrom = LocalDate.parse(entryDataFrom, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    } catch (DateTimeException exception) {
                        System.out.println("Entered data " + entryDataFrom + " id invalid.");
                        break;
                    }
                    System.out.println("Enter 'to' date dd-MM-yyyy");
                    String entryDataTo = scanner.nextLine();
                    LocalDate dataTo = null;
                    try {
                        dataTo = LocalDate.parse(entryDataTo, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    } catch (DateTimeException exception) {
                        System.out.println("Entered data " + entryDataTo + " id invalid.");
                        break;
                    }
                    BigDecimal bigDecimal = service.averageOfExpensesInRangeOfTime(dataFrom, dataTo);
                    System.out.println("Average of yours expenses in given time: " + bigDecimal);
                    break;
                }
                case "averageInCategory": {
                    System.out.println("Enter category");
                    String categoryInput = br.readLine();
                    BigDecimal bigDecimal = service.averageOfExpensesInCategory(categoryInput);

                    System.out.println(bigDecimal);
                    break;
                }
                case "mapAverage": {
                    Map<String, BigDecimal> mapCategoryAndAverage = service.mapOfCategoryAndAverageOfExpenses();

                    System.out.println(mapCategoryAndAverage.entrySet());
                    break;
                }
                case "mapLargest": {
                    Map<String, BigDecimal> mapLargest = service.mapOfCategoryAndLargestExpense();

                    System.out.println(mapLargest.entrySet());

                    break;
                }

                case "x": {
                    System.out.println("Application closed");
                    System.exit(1);
                    break;
                }
                default:
                    System.out.println("Unknown command");
            }
        }
    }
}
