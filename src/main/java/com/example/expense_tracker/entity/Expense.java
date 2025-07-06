package com.example.expense_tracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="description",nullable=false, length =255)
    private String description;

    @Column(name= "amount", nullable = false)
    private BigDecimal amount;

    @Column(name="date", nullable = false)
    private LocalDate date;


    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name= "expense_category",
            joinColumns = @JoinColumn(name= "expense_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    //@Column(name="category", nullable = false, length = 50)
    private Set<Category> categories = new HashSet<>();



}
