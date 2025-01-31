package com.application.api.installment.controllers.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RevenueResponseDTO(
        UUID id,
        String title,
        BigDecimal totalValue,
        Integer quantityInstallments,
        LocalDate initialDate) {
}
