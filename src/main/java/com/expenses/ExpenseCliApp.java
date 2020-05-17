package com.expenses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;


@SuppressWarnings("InfiniteLoopStatement")
public class ExpenseCliApp {

    public static void main(String[] args) throws InvalidExpenseException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Scanner scanner = new Scanner(System.in);
        ExpenseCliMethod method = new ExpenseCliMethod();

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
                case "x": {
                    System.out.println("Application closed");
                    System.exit(0);
                }
                default:
                    System.out.println("Unknown command");
            }
        }
    }
}
