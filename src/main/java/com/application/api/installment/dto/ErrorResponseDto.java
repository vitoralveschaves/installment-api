package com.application.api.installment.dto;

import java.util.List;

public record ErrorResponseDto(
        Integer status,
        String message,
        List<FieldErrorsDto> errors) {
}
