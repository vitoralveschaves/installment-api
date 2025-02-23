package com.application.api.installment.dto;

import com.application.api.installment.entities.Installment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record InstallmentResponseDto(
        UUID id,
        LocalDate currentMonth,
        Integer installmentNumber,
        BigDecimal installmentValue,
        Integer quantityInstallments,
        Boolean isPaid,
        LocalDate initialDate,
        ExpenseResponseDto expense) {

    public InstallmentResponseDto(Installment installment) {
        this(
                installment.getId(),
                installment.getCurrentMonth(),
                installment.getInstallmentNumber(),
                installment.getInstallmentValue(),
                installment.getQuantityInstallments(),
                installment.isPaid(),
                installment.getInitialDate(),
                new ExpenseResponseDto(
                        installment.getExpense().getId(),
                        installment.getExpense().getTitle(),
                        installment.getExpense().getTotalValue(),
                        installment.getExpense().getQuantityInstallments(),
                        installment.getExpense().getInitialDate(),
                        installment.getExpense().getCategory() != null ? installment.getExpense().getCategory().getName() : null
                )
        );
    }
}
