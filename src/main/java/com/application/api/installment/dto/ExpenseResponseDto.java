package com.application.api.installment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ExpenseResponseDto {

    private UUID expenseId;

    private String title;

    private BigDecimal totalValue;

    private Integer quantityInstallments;

    private LocalDate initialDate;

    private String category;
}
