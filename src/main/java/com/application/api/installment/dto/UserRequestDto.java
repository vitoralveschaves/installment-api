package com.application.api.installment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequestDto {

        @NotBlank(message = "Campo obrigatório")
        private String name;

        @Email(message = "Email inválido")
        private String email;

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 8, message = "O campo deve possuir no mínimo 8 caracteres")
        private String password;
}
