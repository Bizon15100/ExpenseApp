package com.expenses.io;

public enum VarType {
    AMOUNT("amount"),
    DATE("date"),
    PLACE("place"),
    CATEGORY("category");


    private String type;

    VarType(String type){
        this.type = type;
    }
}
