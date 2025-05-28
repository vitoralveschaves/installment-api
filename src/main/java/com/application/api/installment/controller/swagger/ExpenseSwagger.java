package com.application.api.installment.controller.swagger;

import com.application.api.installment.configuration.SecurityConfiguration;
import com.application.api.installment.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Despesas", description = "Recurso para registrar, listar, buscar e excluir despesas")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public interface ExpenseSwagger {

    @Operation(
            summary = "Registra uma nova despesa",
            method = "POST",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Despesa registrada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExpenseResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Token inválido ou expirado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Dados nulos ou inválidos",
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
    ResponseEntity<Void> createExpense(String language, ExpenseRequestDto request);

    @Operation(
            summary = "Lista todas as despesas do usuário",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Despesas listadas com sucesso"),
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
    ResponseEntity<List<ExpenseResponseDto>> getExpenses(String language, String search);

    @Operation(
            summary = "Busca despesa pelo id correspondente",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca concluída"),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Token inválido ou expirado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Despesa não encontrada",
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
    ResponseEntity<ExpenseResponseDto> getById(String language, String id);

    @Operation(
            summary = "Exclui despesa pelo id correspondente",
            method = "DELETE",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Despesa excluída com sucesso"),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Token inválido ou expirado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Despesa não encontrada",
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
    ResponseEntity<Void> delete(String language, String id);

    @Operation(
            summary = "Atualiza despesa pelo id correspondente",
            method = "PATCH",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Despesa atualizada com sucesso"),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Token inválido ou expirado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Despesa não encontrada",
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
    ResponseEntity<Void> update(String language, String id, ExpenseUpdateDto request);
}
