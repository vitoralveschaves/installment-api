package com.application.api.installment.controllers.dto;

import java.util.List;

public record ErrorResponseDTO(
        Integer status,
        String message,
        List<FieldErrors> errors) {
}
