package com.expenses.io;

public enum FileType {
    CSV("csv") {
        @Override
        ExpenseMapper getMapper() {
            return new ExpenseCsvMapper();
        }
    },
    JSON("json") {
        @Override
        ExpenseMapper getMapper() {
            return new ExpenseJsonMapper();
        }
    };

    private String extension;

    FileType(String extension) {
        this.extension = extension;
    }

    abstract ExpenseMapper getMapper();
}
