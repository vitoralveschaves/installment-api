package com.application.api.installment.converter;

import com.application.api.installment.dto.InstallmentResponseDto;
import com.application.api.installment.model.Installment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class InstallmentResponseConverter implements Function<Installment, InstallmentResponseDto> {

    private final ExpenseResponseConverter expenseResponseConverter;

    @Override
    public InstallmentResponseDto apply(Installment installment) {
        return InstallmentResponseDto.builder()
                .installmentId(installment.getId())
                .currentMonth(installment.getCurrentMonth())
                .installmentNumber(installment.getInstallmentNumber())
                .installmentValue(installment.getInstallmentValue())
                .quantityInstallments(installment.getQuantityInstallments())
                .isPaid(installment.isPaid())
                .initialDate(installment.getInitialDate())
                .expense(expenseResponseConverter.apply(installment.getExpense()))
                .build();
    }
}
