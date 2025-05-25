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

        @NotBlank(message = "Required field")
        private String name;

        @NotBlank(message = "Required field")
        @Email(message = "Invalid email")
        private String email;

        @NotBlank(message = "Required field")
        @Size(min = 8, message = "Password must contain at least 8 characters")
        private String password;
}
