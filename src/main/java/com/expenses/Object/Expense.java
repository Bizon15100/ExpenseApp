package com.expenses.Object;

import com.expenses.InvalidExpenseException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Objects;

@Getter
public class Expense {
    @JsonProperty
    private BigDecimal amount;
    @JsonProperty
    private String category;
    @JsonProperty
    private String place;
    @JsonProperty()
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;


    public static final class Builder {
        private BigDecimal amount;
        private String category;
        private String place;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;

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
            if (place.isBlank()) {
                throw new InvalidExpenseException("You have to write a place of expense.");
            }
            if (!checkPrecisionOfDouble(String.valueOf(amount))) {
                throw new InvalidExpenseException("0.00");
            }
            Expense expense = new Expense();
            expense.amount = this.amount;
            expense.category = this.category;
            expense.place = this.place;
            expense.date = this.date;
            return expense;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Objects.equals(amount, expense.amount) &&
                Objects.equals(date, expense.date) &&
                Objects.equals(place, expense.place) &&
                Objects.equals(category, expense.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, date, place, category);
    }


    static boolean checkPrecisionOfDouble(String attribute) {
        String s = String.valueOf(attribute);
        String[] split = s.split("\\.");
        if (!s.contains(".")) {
            return true;
        } else if (split.length <= 2) {
            return split[1].length() == 2 | split[1].length() == 1 | split[1].isEmpty();
        } else return false;

    }

    public static Builder builder() {
        return new Builder();
    }
}