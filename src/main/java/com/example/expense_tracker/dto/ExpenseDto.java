package com.example.expense_tracker.dto;

import com.example.expense_tracker.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {
    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private Set<String> categories;
}
