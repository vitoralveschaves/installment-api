package com.application.api.installment.controllers.swagger;

import com.application.api.installment.dto.ErrorResponseDto;
import com.application.api.installment.dto.LoginRequestDto;
import com.application.api.installment.dto.LoginResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Autenticação", description = "Recurso para se autenticar na aplicação")
public interface AuthSwagger {

    @Operation(
            summary = "Autentica usuário",
            method = "POST",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário logado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Dados nulos ou inválidos",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado",
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
    ResponseEntity<LoginResponseDto> login(LoginRequestDto request);

}
