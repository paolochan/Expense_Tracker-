package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.ExpenseDto;
import com.example.expense_tracker.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/expenses")
public class ExpenseController {

    private ExpenseService expenseService;

    // Build Add Expense REST API
    @PostMapping
    public ResponseEntity<ExpenseDto> createExpense(@RequestBody ExpenseDto expenseDto) {
       ExpenseDto savedExpense = expenseService.createExpense(expenseDto);
       return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    /*public ResponseEntity<Set<String>> getExpenseCategories(@PathVariable("id") Long expenseId){
        ExpenseDto expenseDto = expenseService.getExpenseById(expenseId);
        return new ResponseEntity<>(expenseDto.getCategories(), HttpStatus.OK);
    }*/

    //Build Get Expense REST API
    @GetMapping("{id}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable("id") Long expenseId) {
        ExpenseDto expenseDto = expenseService.getExpenseById(expenseId);
        return ResponseEntity.ok(expenseDto);
    }

    //Build get all employess REST API
    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getAllExpenses(){
        List<ExpenseDto> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }

    //Build Update expense REST API
    @PutMapping("{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable("id") Long expenseId,
                                                    @RequestBody ExpenseDto updateExpense) {
        ExpenseDto expenseDto = expenseService.updateExpense(expenseId, updateExpense);
        return ResponseEntity.ok(expenseDto);
    }

    //Build delete expense REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable("id") Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.ok("Deleted expense");
    }

}
