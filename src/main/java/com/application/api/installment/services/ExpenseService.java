package com.application.api.installment.services;

import com.application.api.installment.entities.Expense;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    Expense createExpense(Expense expenses);
    List<Expense> getExpenses(String search);
    Expense getById(UUID id);
    void delete(UUID id);
    void update(Expense expense);
}
