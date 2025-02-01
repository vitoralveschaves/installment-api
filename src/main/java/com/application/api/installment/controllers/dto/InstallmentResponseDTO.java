package com.application.api.installment.controllers.dto;

import com.application.api.installment.entities.Installment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record InstallmentResponseDTO(
        UUID id,
        LocalDate currentMonth,
        Integer installmentNumber,
        BigDecimal installmentValue,
        Integer quantityInstallments,
        Boolean isPaid,
        LocalDate initialDate,
        ExpenseResponseDTO expense) {

    public InstallmentResponseDTO(Installment installment) {
        this(
                installment.getId(),
                installment.getCurrentMonth(),
                installment.getInstallmentNumber(),
                installment.getInstallmentValue(),
                installment.getQuantityInstallments(),
                installment.isPaid(),
                installment.getInitialDate(),
                new ExpenseResponseDTO(
                        installment.getExpense().getId(),
                        installment.getExpense().getTitle(),
                        installment.getExpense().getTotalValue(),
                        installment.getExpense().getQuantityInstallments(),
                        installment.getExpense().getInitialDate()
                )
        );
    }
}
