package com.application.api.installment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
public class InstallmentBalanceResponseDto {
    private BigDecimal totalToPay;
}
