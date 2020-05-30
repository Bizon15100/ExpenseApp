package com.expenses;

import java.io.*;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;




public class ExpenseCliApp {

    public static void main(String[] args) throws IOException, InvalidExpenseException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ExpenseCliMethod method = new ExpenseCliMethod();
        ExpenseService service = new ExpenseService();

        System.out.println("Welcome in my app.");

        System.out.println("Enter file name - format 'name.json or name.csv' ");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();

        if (!new File(fileName).exists()) {
             new FileWriter(fileName);
            System.out.println("New file created");
        }else method.load(fileName);

        while (true) {
            method.menu();
            System.out.println("Insert command");
            String command = br.readLine();
            switch (command) {
                case "add": {
                    method.Add();
                    break;
                }
                case "quickAdd": {
                    method.quickAdd();
                }
                case "expenseAll": {
                method.ExpenseAll();
                break;
                }
                case "findCategory": {
                    method.FindCategory();
                    break;
                }
                case "findRange": {
                    method.FindRange();
                    break;
                }
                case "nLargest": {
                    method.NLargest();
                    break;
                }
                case "averageFromTo": {
                    method.AverageFromTo();
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
                case "save": {
                    method.save(fileName);
                    break;
                }
                case "x": {
                   method.save(fileName);
                    System.out.println("Application closed");
                    System.exit(0);
                }
                default:
                    System.out.println("Unknown command");
            }
        }

    }
}
