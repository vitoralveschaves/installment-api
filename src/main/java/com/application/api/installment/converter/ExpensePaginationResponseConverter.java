package com.application.api.installment.converter;

import com.application.api.installment.dto.ExpenseResponseDto;
import com.application.api.installment.dto.PageableResponseDto;
import com.application.api.installment.dto.PaginationResponseDto;
import com.application.api.installment.model.Expense;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpensePaginationResponseConverter {

    private final ExpenseResponseConverter expenseResponseConverter;
    private final PageableResponseConverter pageableResponseConverter;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpensePaginationResponseConverter.class);

    public PaginationResponseDto<ExpenseResponseDto> apply(Page<Expense> expensePage) {

        LOGGER.info("stage=init method=ExpensePaginationResponseConverter.apply");

        PageableResponseDto pageable = pageableResponseConverter.apply(expensePage);

        var expenses = expensePage.getContent();

        PaginationResponseDto<ExpenseResponseDto> response = PaginationResponseDto.<ExpenseResponseDto>builder()
                .page(pageable)
                .content(expenses.stream().map(expenseResponseConverter).toList())
                .build();

        LOGGER.info("stage=end method=ExpensePaginationResponseConverter.apply");
        return response;
    }
}
