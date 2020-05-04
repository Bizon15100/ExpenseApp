package com.expenses;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Expense {
    private double amount;
    private LocalDate date;
    private String place;
    private String category;

    public Expense(double amount, LocalDate date,
                   String place, String category) {
        this.amount = amount;
        this.date = date;
        this.place = place;
        this.category = category;
    }
    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public String getCategory() {
        return category;
    }


    @Override
    public String toString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String message = "{%s , %s , %s , %s}";
        return String.format(message, date.format(dateFormat), amount, place, category);
    }

    public static Expense from(double amount, LocalDate date,
                               String place, String category) throws InvalidExpenseException{
        if (amount<0){
            throw new InvalidExpenseException("Amount should be grater than 0.");
        }
        if (date == null || date.isAfter(LocalDate.now())) {
                throw new InvalidExpenseException("You have to write a past date.");
        }
        if (place.isBlank() ){
            throw new InvalidExpenseException("You have to write a place of expense.");
        }

        if(!checkPrecisionOfDouble(amount)){
            throw new InvalidExpenseException("Money requires to be written in format 0.00");
        }

        return new Expense(amount,date,place,category);
    }

    static boolean checkPrecisionOfDouble (double attribute) {
        String s = String.valueOf(attribute);
        String[] split = s.split("\\.");
        if (split.length <=2) {
            return split[1].length() == 2 | split[1].length() == 1 | split[1].length() == 0;
        } else return false;

    }
    static boolean checkPrecisionOfDouble2 (BigDecimal attribute) {
        int precision = attribute.precision();
        return precision>=0 && precision<=2;
        // wywala mi błąd w testach gdy używam w metodzie from...
        // nie wiem jak ją poprawnie zaimplementować ...
    }
}
