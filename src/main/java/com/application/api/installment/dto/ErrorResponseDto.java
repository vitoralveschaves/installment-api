package com.application.api.installment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(description = "Modelo de resposta para erros")
public class ErrorResponseDto {

        @Schema(description = "Código de status HTTP")
        private Integer status;

        @Schema(description = "Mensagem de erro")
        private String message;

        @Schema(description = "Listagem de erros")
        private List<FieldErrorsDto> errors;
}
