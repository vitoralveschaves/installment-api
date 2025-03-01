package com.application.api.installment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Modelo de resposta para erros")
public record ErrorResponseDto(
        @Schema(description = "Código de status HTTP")
        Integer status,
        @Schema(description = "Mensagem de erro")
        String message,
        @Schema(description = "Listagem de erros")
        List<FieldErrorsDto> errors) {
}
