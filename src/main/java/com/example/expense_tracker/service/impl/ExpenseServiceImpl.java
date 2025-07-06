package com.example.expense_tracker.service.impl;

import com.example.expense_tracker.dto.ExpenseDto;
import com.example.expense_tracker.entity.Category;
import com.example.expense_tracker.entity.Expense;
import com.example.expense_tracker.exception.ResourceNotFoundException;
import com.example.expense_tracker.mapper.ExpenseMapper;
import com.example.expense_tracker.repository.CategoryRepository;
import com.example.expense_tracker.repository.ExpenseRepository;
import com.example.expense_tracker.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepository expenseRepository;
    private CategoryRepository categoryRepository;
    @Override
    public ExpenseDto createExpense(ExpenseDto expenseDto) {
        Expense expense = ExpenseMapper.mapToExpense(expenseDto);

        // 2) look up each named category and collect managed entities
        Set<Category> cats = expenseDto.getCategories().stream()
                .map(name -> categoryRepository.findByName(name)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Unknown category: " + name)))
                .collect(Collectors.toSet());


        expense.setCategories(cats);
        Expense savedExpense =expenseRepository.save(expense);
        return ExpenseMapper.mapToExpenseDto(savedExpense);
    }

    @Override
    public ExpenseDto getExpenseById(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense with given id: " + expenseId + " not found"));
        return ExpenseMapper.mapToExpenseDto(expense);
    }

    @Override
    public List<ExpenseDto> getAllExpenses() {
        List<Expense> expenses= expenseRepository.findAll();
        return expenses.stream().map((expense) -> ExpenseMapper.mapToExpenseDto(expense))
                .collect(Collectors.toList());
    }

    @Override
    public ExpenseDto updateExpense(Long expenseId, ExpenseDto updatedExpense) {
       Expense expense = expenseRepository.findById(expenseId).
                orElseThrow(() -> new ResourceNotFoundException("Expense with id: " + expenseId + " not found"));
        expense.setDescription(updatedExpense.getDescription());
        expense.setAmount(updatedExpense.getAmount());
        expense.setDate(updatedExpense.getDate());

        Set<Category> cats = updatedExpense.getCategories().stream()
                .map(name -> categoryRepository.findByName(name)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Unknown category: " + name)))
                .collect(Collectors.toSet());
        expense.setCategories(cats);

        Expense saved = expenseRepository.save(expense);

        return ExpenseMapper.mapToExpenseDto(saved);
    }

    @Override
    public void deleteExpense(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense with id: " + expenseId + " not found"));
        expenseRepository.deleteById(expenseId);
    }


}
