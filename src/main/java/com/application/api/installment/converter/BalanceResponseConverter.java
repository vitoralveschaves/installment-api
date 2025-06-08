package com.application.api.installment.converter;

import com.application.api.installment.dto.InstallmentBalanceResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class BalanceResponseConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BalanceResponseConverter.class);

    public InstallmentBalanceResponseDto apply(List<Object[]> balances) {

        LOGGER.info("stage=init method=BalanceResponseConverter.apply");

        if(balances.isEmpty()) {
            LOGGER.info("stage=end method=BalanceResponseConverter.apply message=Balances is empty");
            return InstallmentBalanceResponseDto.builder()
                    .totalToPayByMonth(BigDecimal.ZERO)
                    .totalToPay(BigDecimal.ZERO)
                    .build();
        }

        var balanceResult = balances.getFirst();

        BigDecimal totalToPayByMonth = (BigDecimal) balanceResult[0];
        BigDecimal totalToPay = (BigDecimal) balanceResult[1];

        var response = InstallmentBalanceResponseDto.builder()
                .totalToPayByMonth(totalToPayByMonth == null ? BigDecimal.ZERO : totalToPayByMonth)
                .totalToPay(totalToPay == null ? BigDecimal.ZERO : totalToPay)
                .build();

        LOGGER.info("stage=end method=BalanceResponseConverter.apply response={}", response);

        return response;
    }
}
