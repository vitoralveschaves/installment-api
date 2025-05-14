package com.application.api.installment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExpenseRequestDto {

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 2, max = 200)
        private String title;

        @NotNull(message = "Campo obrigatório")
        @Positive(message = "Campo deve possuir um valor positivo")
        private BigDecimal totalValue;

        @Positive(message = "Campo deve possuir um valor positivo")
        private Integer quantityInstallments;

        private UUID categoryId;

        @NotNull(message = "Campo obrigatório")
        @PastOrPresent(message = "O campo não pode ser uma data futura")
        private LocalDate initialDate;
}
