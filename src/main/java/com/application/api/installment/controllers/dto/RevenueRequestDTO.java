package com.application.api.installment.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RevenueRequestDTO(
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 2, max = 200)
        String title,
        @NotNull(message = "Campo obrigatório")
        @Positive(message = "Campo deve possuir um valor positivo")
        BigDecimal totalValue,
        @Positive(message = "Campo deve possuir um valor positivo")
        Integer quantityInstallments,
        @NotNull(message = "Campo obrigatório")
        @PastOrPresent(message = "O campo não pode ser uma data futura")
        LocalDate initialDate) {
}
