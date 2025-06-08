package com.application.api.installment.controller.swagger;

import com.application.api.installment.configuration.SecurityConfiguration;
import com.application.api.installment.dto.ErrorResponseDto;
import com.application.api.installment.dto.PaginationResponseDto;
import com.application.api.installment.dto.UserRequestDto;
import com.application.api.installment.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Usuários", description = "Recurso para registrar, listar, buscar e excluir usuários")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public interface UserSwagger {

    @Operation(
            summary = "Registra um novo usuário",
            method = "POST",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuário registrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Token inválido ou expirado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Usuário já existe",
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
    ResponseEntity<Void> register(String language, UserRequestDto request);

    @Operation(
            summary = "Lista todos os usuários",
            method = "GET",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuários listados com sucesso"
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
    ResponseEntity<PaginationResponseDto<UserResponseDto>> getAll(String language, Integer page, Integer pageSize);

    @Operation(
            summary = "Busca usuário pelo id correspondente",
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
                            description = "Usuário não encontrado",
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
    ResponseEntity<UserResponseDto> getById(String language, String id);

    @Operation(
            summary = "Busca usuário pelo email correspondente",
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
                            description = "Usuário não encontrado",
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
    ResponseEntity<UserResponseDto> getByEmail(String language, String email);

    @Operation(
            summary = "Ativa usuário pelo email correspondente",
            method = "PATCH",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário ativado com sucesso"),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Token inválido ou expirado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
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
    ResponseEntity<Void> activeByEmail(String language, String email);

    @Operation(
            summary = "Exclui usuário pelo id correspondente",
            method = "DELETE",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário excluída com sucesso"),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Token inválido ou expirado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
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
    ResponseEntity<Void> deleteById(String language, String id);

    @Operation(
            summary = "Exclui usuário pelo email correspondente",
            method = "DELETE",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário excluída com sucesso"),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Token inválido ou expirado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
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
    ResponseEntity<Void> deleteByEmail(String language, String email);
}
