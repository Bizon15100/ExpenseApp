package com.expenses.io;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum SortType {
    ASC("ascending"),
    DSC("descending");


    private String type;

    SortType(String type){
        this.type = type;
    }


}
