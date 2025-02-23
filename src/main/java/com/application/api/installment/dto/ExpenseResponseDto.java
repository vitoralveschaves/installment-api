package com.application.api.installment.dto;

import com.application.api.installment.entities.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseResponseDto(
        UUID id,
        String title,
        BigDecimal totalValue,
        Integer quantityInstallments,
        LocalDate initialDate,
        String category) {
    public ExpenseResponseDto(Expense expense) {
        this(expense.getId(), expense.getTitle(), expense.getTotalValue(),
                expense.getQuantityInstallments(), expense.getInitialDate(),
                expense.getCategory() != null ? expense.getCategory().getName() : null
        );
    }
}
