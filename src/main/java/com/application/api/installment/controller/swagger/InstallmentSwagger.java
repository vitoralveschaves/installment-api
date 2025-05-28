package com.application.api.installment.controller.swagger;

import com.application.api.installment.configuration.SecurityConfiguration;
import com.application.api.installment.dto.ErrorResponseDto;
import com.application.api.installment.dto.InstallmentBalanceResponseDto;
import com.application.api.installment.dto.InstallmentResponseDto;
import com.application.api.installment.dto.PaginationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Parcelas", description = "Recurso para buscar parcelas com paginação e filtros dinâmicos")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
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
    ResponseEntity<PaginationResponseDto<InstallmentResponseDto>> getInstallments(
            String language,
            Integer page,
            Integer pageSize,
            String month,
            String year,
            String search,
            String category
    );

    @Operation(
            summary = "Calcula as dividas do mês ou de todas as parcelas",
            method = "GET",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Balanço calculado com sucesso"
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
    ResponseEntity<InstallmentBalanceResponseDto> getExpenseBalance(String language, String month);

    @Operation(
            summary = "Paga uma parcela",
            method = "Patch",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Parcela paga com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Token inválido ou expirado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Parcela não encontrada",
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
    ResponseEntity<Void> pay(String language, String installmentId);
}
