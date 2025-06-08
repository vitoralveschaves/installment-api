package com.application.api.installment.service;

import com.application.api.installment.dto.ExpenseRequestDto;
import com.application.api.installment.dto.ExpenseResponseDto;
import com.application.api.installment.dto.ExpenseUpdateDto;
import com.application.api.installment.dto.PaginationResponseDto;

public interface ExpenseService {
    ExpenseResponseDto createExpense(ExpenseRequestDto request);
    PaginationResponseDto<ExpenseResponseDto> getExpenses(Integer page, Integer pageSize, String search);
    ExpenseResponseDto getById(String id);
    void delete(String id);
    void update(String id, ExpenseUpdateDto request);
}
