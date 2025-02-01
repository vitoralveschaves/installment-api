package com.application.api.installment.controllers.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseResponseDTO(
        UUID id,
        String title,
        BigDecimal totalValue,
        Integer quantityInstallments,
        String category,
        LocalDate initialDate) {
}
