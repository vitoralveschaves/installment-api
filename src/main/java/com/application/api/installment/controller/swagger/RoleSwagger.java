package com.application.api.installment.controller.swagger;

import com.application.api.installment.configuration.SecurityConfiguration;
import com.application.api.installment.dto.ErrorResponseDto;
import com.application.api.installment.dto.RoleRequestDto;
import com.application.api.installment.dto.RoleResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Acessos", description = "Recurso para adicionar e listar acessos")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public interface RoleSwagger {

    @Operation(
            summary = "Registra um novo acesso",
            method = "POST",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Acesso registrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Token inválido ou expirado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Acesso já existe",
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
    ResponseEntity<Void> createRole(RoleRequestDto request);

    @Operation(
            summary = "Lista todos os acessos",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Acessos listadas com sucesso"),
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
    ResponseEntity<List<RoleResponseDto>> getAllRoles();

    @Operation(
            summary = "Adiciona acesso a um usuário",
            method = "PUT",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Acesso adicionado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Token inválido ou expirado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Acesso ou usuário não encontrado",
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
    void addRoleToUser(String id, RoleRequestDto request);

}
