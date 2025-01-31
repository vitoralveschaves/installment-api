package com.application.api.installment.controllers.dto;

import jakarta.validation.constraints.NotBlank;

public record RevenueUpdateDTO(@NotBlank String title) {
}
