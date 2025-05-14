package com.application.api.installment.service;

import com.application.api.installment.dto.ExpenseRequestDto;
import com.application.api.installment.dto.ExpenseResponseDto;
import com.application.api.installment.dto.ExpenseUpdateDto;
import com.application.api.installment.model.Expense;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    ExpenseResponseDto createExpense(ExpenseRequestDto request);
    List<ExpenseResponseDto> getExpenses(String search);
    ExpenseResponseDto getById(UUID id);
    void delete(UUID id);
    void update(UUID id, ExpenseUpdateDto request);
}
