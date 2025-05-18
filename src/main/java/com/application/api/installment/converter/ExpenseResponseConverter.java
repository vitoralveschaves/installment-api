package com.application.api.installment.converter;

import com.application.api.installment.dto.ExpenseResponseDto;
import com.application.api.installment.model.Expense;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ExpenseResponseConverter implements Function<Expense, ExpenseResponseDto> {

    @Override
    public ExpenseResponseDto apply(Expense expense) {
        return ExpenseResponseDto.builder()
                .expenseId(expense.getId())
                .title(expense.getTitle())
                .totalValue(expense.getTotalValue())
                .quantityInstallments(expense.getQuantityInstallments())
                .initialDate(expense.getInitialDate())
                .category(buildCategory(expense))
                .build();
    }

    private String buildCategory(Expense expense) {
        if(expense.getCategory() == null) {
            return null;
        }
        return expense.getCategory().getName();
    }
}
