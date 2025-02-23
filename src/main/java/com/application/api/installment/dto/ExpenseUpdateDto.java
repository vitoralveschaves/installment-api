package com.application.api.installment.dto;

import jakarta.validation.constraints.NotBlank;

public record ExpenseUpdateDto(@NotBlank String title) {
}
