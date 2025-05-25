package com.application.api.installment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChangePasswordRequestDto {

    @NotBlank(message = "Invalid password")
    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String oldPassword;

    @NotBlank(message = "Invalid password")
    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String newPassword;
}
