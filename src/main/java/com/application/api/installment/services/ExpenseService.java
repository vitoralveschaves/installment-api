package com.application.api.installment.services;

import com.application.api.installment.dto.ExpenseRequestDto;
import com.application.api.installment.dto.ExpenseResponseDto;
import com.application.api.installment.dto.ExpenseUpdateDto;
import com.application.api.installment.entities.Expense;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    Expense createExpense(ExpenseRequestDto request);
    List<ExpenseResponseDto> getExpenses(String search);
    ExpenseResponseDto getById(UUID id);
    void delete(UUID id);
    void update(UUID id, ExpenseUpdateDto request);
}
