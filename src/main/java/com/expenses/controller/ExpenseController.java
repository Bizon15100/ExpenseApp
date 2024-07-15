package com.expenses.controller;

import com.expenses.Object.Expense;
import com.expenses.Object.ExpensePrev;
import com.expenses.io.SortType;
import com.expenses.service.ExpenseCliMethod;
import com.expenses.InvalidExpenseException;
import com.expenses.io.VarType;
import com.expenses.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class ExpenseController {

    @Autowired
    ExpenseCliMethod methods = new ExpenseCliMethod();

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

    @RequestMapping(method = RequestMethod.POST, path = "/add")
    public ResponseEntity<Expense> addExpense(@RequestBody ExpensePrev expense) throws InvalidExpenseException {
        Expense paredExpense = services.addExpense(expense);
        return ResponseEntity.of(Optional.of(paredExpense));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findByDate")
    public ResponseEntity<Set<Expense>> findExpensesByDate(@RequestBody String dateFrom, @RequestBody String dateTo) {
        Set<Expense> paredExpense = services.findExpensesInRange(dateFrom, dateTo);
        return ResponseEntity.of(Optional.of(paredExpense));
    }

}
