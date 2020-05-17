package com.expenses;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class  Expense {
    private BigDecimal amount;
    private LocalDate date;
    private String place;
    private String category;

    public BigDecimal getAmount() {
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


    public static final class Builder {
        private BigDecimal amount;
        private LocalDate date;
        private String place;
        private String category;

        public LocalDate getDate(){
            return this.date;
        }
        public BigDecimal getAmount(){
            return this.amount;
        }
        public String getPlace(){
            return this.place;
        }
        public String getCategory(){
            return this.category;
        }

        public Builder amount(BigDecimal amount) {
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

        public Expense build() throws InvalidExpenseException {

            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new InvalidExpenseException("Amount should be grater than 0.");
            }
            if (date == null || date.isAfter(LocalDate.now())) {
                throw new InvalidExpenseException("You have to write a past date.");
            }
            if (place.isBlank() && place==null) {
                throw new InvalidExpenseException("You have to write a place of expense.");
            }

            if (!checkPrecisionOfDouble(String.valueOf(amount))) {
                throw new InvalidExpenseException("0.00");
            }
            Expense expense = new Expense();
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
        if (!s.contains(".")){
            return true;
        } else if (split.length <= 2) {
            return split[1].length() == 2 | split[1].length() == 1 | split[1].length() == 0;
        } else return false;

    }

    public static Builder builder(){
        return new Builder();
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormat = DateTimeFormatter
                .ofPattern("dd-MM-yyyy");
        String message = "{%s, %s, %s, %s}";

        return String.format(message,
                date.format(dateFormat), amount, place, category);
    }

}

//for (Expense expense : expenses) {
//        message.append("|Amount|: ").append(expense.getAmount()).append(" ")
//        .append("|Date|: ").append(expense.getDate()).append(" ")
//        .append("|Place|: ").append(expense.getPlace()).append(" ")
//        .append("|Category|: ").append(expense.getCategory()).append(" ")
//        .append("\n");
//        }