package com.application.api.installment.controllers.swagger;

import com.application.api.installment.config.SecurityConfig;
import com.application.api.installment.dto.ErrorResponseDto;
import com.application.api.installment.dto.InstallmentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

@Tag(name = "Parcelas", description = "Recurso para buscar parcelas com paginação e filtros dinâmicos")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public interface InstallmentSwagger {

    @Operation(
            summary = "Lista as parcelas de um usuário de acordo com o mês atual",
            method = "GET",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Parcelas listadas com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Token inválido ou expirado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro no servidor",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class))
                    )
            }
    )
    ResponseEntity<Page<InstallmentResponseDto>> getInstallments(
            Integer page,
            Integer pageSize,
            String month,
            String year,
            String search,
            String category
    );
}
