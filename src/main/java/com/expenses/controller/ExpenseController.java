package com.expenses.controller;

import com.expenses.Object.Expense;
import com.expenses.type.SortType;
import com.expenses.InvalidExpenseException;
import com.expenses.type.VarType;
import com.expenses.service.ExpenseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class ExpenseController {

    @Autowired
    ExpenseService services = new ExpenseService();

    @RequestMapping(method = RequestMethod.GET, path = "/all")
    public ResponseEntity<Set<Expense>> getExpense() {
        return ResponseEntity.of(Optional.ofNullable(services.getExpenseSet()));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/all/sorted")
    public ResponseEntity<Set<Expense>> getExpenseSortedBy(@RequestBody VarType type, @RequestBody SortType ascOrDesc) {
        return ResponseEntity.of(Optional.ofNullable(services.sortByObject(type, ascOrDesc.getType())));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, path = "/add")
    public ResponseEntity<Expense> addExpense(@RequestBody String stringExpense) throws InvalidExpenseException, JsonProcessingException {
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        Expense expense = objectMapper.readValue(stringExpense, Expense.class);
        Expense paredExpense = services.addExpense(expense);
        return ResponseEntity.of(Optional.of(paredExpense));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findByDate")
    public ResponseEntity<Set<Expense>> findExpensesByDate(@RequestBody String dateFrom, @RequestBody String dateTo) {
        Set<Expense> paredExpense = services.findExpensesInRange(dateFrom, dateTo);
        return ResponseEntity.of(Optional.of(paredExpense));
    }

}
