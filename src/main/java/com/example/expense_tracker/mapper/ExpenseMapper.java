package com.example.expense_tracker.mapper;

import com.example.expense_tracker.dto.ExpenseDto;
import com.example.expense_tracker.entity.Expense;
import com.example.expense_tracker.entity.Category;

import java.util.Set;
import java.util.stream.Collectors;

public class ExpenseMapper {

    public static ExpenseDto mapToExpenseDto(Expense expense) {
        // Convert Set<Category> → Set<String> of category names
        Set<String> categoryNames = expense.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toSet());

        return new ExpenseDto(
                expense.getId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getDate(),
                categoryNames
        );
    }

    public static Expense mapToExpense(ExpenseDto expenseDto) {
        // Convert Set<String> → Set<Category>
        Set<Category> categories = expenseDto.getCategories().stream()
                .map(name -> {
                    Category c = new Category();
                    c.setName(name);
                    return c;
                })
                .collect(Collectors.toSet());

        Expense expense = new Expense();
        expense.setId(expenseDto.getId());
        expense.setDescription(expenseDto.getDescription());
        expense.setAmount(expenseDto.getAmount());
        expense.setDate(expenseDto.getDate());
        expense.setCategories(categories);
        return expense;

    }
}
