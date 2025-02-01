package com.application.api.installment.controllers.dto;

import jakarta.validation.constraints.NotBlank;

public record ExpenseUpdateDTO(@NotBlank String title) {
}
