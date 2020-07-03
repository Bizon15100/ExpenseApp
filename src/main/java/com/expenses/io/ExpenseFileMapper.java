package com.expenses.io;

import com.expenses.Expense;
import com.expenses.InvalidExpenseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class ExpenseFileMapper {
    public void writeToFile(String fileName,FileType fileType, Set<Expense> expenses) throws IOException{
      //  FileWriter fileWriter = null;
        ExpenseMapper expenseMapper = fileType.getMapper();
        try (FileWriter fileWriter = new FileWriter(fileName)){
            expenseMapper.write(expenses,fileWriter);
        }
    }
    public Set<Expense> readFromFile(String fileName, FileType fileType) throws IOException, InvalidExpenseException {
        ExpenseMapper expenseMapper = fileType.getMapper();

        try (FileReader fileReader = new FileReader(fileName)){
            return expenseMapper.read(fileReader);
        }
    }
}
