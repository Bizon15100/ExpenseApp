package com.expenses;


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

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String categoty) {
        this.category = categoty;
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
            throw new InvalidExpenseException("Amount should be grater than 0");
        }
        if (date == null || date.isAfter(LocalDate.now())) {
                throw new InvalidExpenseException("You can't set a future date of expense");
        }
        if (place.isBlank() ){
            throw new InvalidExpenseException("You have to write a place of expense");
        }

        if(!checkPrecisionOfDouble(amount)){
            throw new InvalidExpenseException("Money requires to be written in format 0.00");
        }

        return new Expense(amount,date,place,category);
    }

    static boolean checkPrecisionOfDouble (Double attribute) {
        String s = String.valueOf(attribute);
        String[] split = s.split("\\.");

        return split[1].length() == 2 || split[1].length() == 1 || split[1].length() == 0;
    }
}
