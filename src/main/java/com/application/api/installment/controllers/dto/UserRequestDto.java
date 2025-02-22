package com.application.api.installment.controllers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
        @NotBlank(message = "Campo obrigatório")
        String name,
        @Email(message = "Email inválido")
        String email,
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 8, message = "O campo deve possuir no mínimo 8 caracteres")
        String password) {
}
