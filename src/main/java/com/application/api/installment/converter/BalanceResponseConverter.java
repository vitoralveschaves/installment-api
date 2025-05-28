package com.application.api.installment.converter;

import com.application.api.installment.dto.InstallmentBalanceResponseDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class BalanceResponseConverter {

    public InstallmentBalanceResponseDto apply(List<Object[]> balances) {

        if(balances.isEmpty()) {
            return InstallmentBalanceResponseDto.builder()
                    .totalToPayByMonth(BigDecimal.ZERO)
                    .totalToPay(BigDecimal.ZERO)
                    .build();
        }

        var balanceResult = balances.getFirst();

        BigDecimal totalToPayByMonth = (BigDecimal) balanceResult[0];
        BigDecimal totalToPay = (BigDecimal) balanceResult[1];

        return InstallmentBalanceResponseDto.builder()
                .totalToPayByMonth(totalToPayByMonth == null ? BigDecimal.ZERO : totalToPayByMonth)
                .totalToPay(totalToPay == null ? BigDecimal.ZERO : totalToPay)
                .build();
    }
}
