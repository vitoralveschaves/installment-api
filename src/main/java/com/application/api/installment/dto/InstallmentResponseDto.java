package com.application.api.installment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstallmentResponseDto {

    private UUID id;

    private LocalDate currentMonth;

    private Integer installmentNumber;

    private BigDecimal installmentValue;

    private Integer quantityInstallments;

    private Boolean isPaid;

    private LocalDate initialDate;

    private ExpenseResponseDto expense;
}
