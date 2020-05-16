package com.expenses;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;


public class  Expense {

    public static final class Builder {
        private double amount;
        private LocalDate date;
        private String place;
        private String category;

        public LocalDate getDate(){
            return this.date;
        }
        public double getAmount(){
            return this.amount;
        }
        public String getPlace(){
            return this.place;
        }
        public String getCategory(){
            return this.category;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder place(String place) {
            this.place = place;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder build() throws InvalidExpenseException {

            if (amount < 0) {
                throw new InvalidExpenseException("Amount should be grater than 0.");
            }
            if (date == null || date.isAfter(LocalDate.now())) {
                throw new InvalidExpenseException("You have to write a past date.");
            }
            if (place.isBlank()) {
                throw new InvalidExpenseException("You have to write a place of expense.");
            }

            if (!checkPrecisionOfDouble(String.valueOf(amount))) {
                throw new InvalidExpenseException("0.00");
            }
            Builder expense = new Builder();
            expense.amount = this.amount;
            expense.date = this.date;
            expense.place = this.place;
            expense.category = this.category;
            return expense;
        }

    }


    static boolean checkPrecisionOfDouble(String attribute) {
        String s = String.valueOf(attribute);
        String[] split = s.split("\\.");
        if (split.length <= 2) {
            return split[1].length() == 2 | split[1].length() == 1 | split[1].length() == 0;
        } else return false;

    }

    public static Builder builder(){
        return new Builder();
    }

    private Expense(){
    }

}
