package com.application.api.installment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryRequestDto{

    @NotBlank(message = "Campo obrigatório")
    private String name;
}
