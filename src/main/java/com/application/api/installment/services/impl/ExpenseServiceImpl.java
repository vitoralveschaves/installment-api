package com.application.api.installment.services.impl;

import com.application.api.installment.entities.Expense;
import com.application.api.installment.entities.Installment;
import com.application.api.installment.exceptions.ExpenseNotFoundException;
import com.application.api.installment.repositories.InstallmentRepository;
import com.application.api.installment.repositories.ExpenseRepository;
import com.application.api.installment.repositories.specification.ExpenseSpecification;
import com.application.api.installment.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final InstallmentRepository installmentRepository;

    @Override
    @Transactional
    public Expense createExpense(Expense expense) {
        Expense expenseSaved = expenseRepository.save(expense);
        var result = expenseSaved
                .getTotalValue()
                .divide(
                        BigDecimal.valueOf(expenseSaved.getQuantityInstallments()),
                        3,
                        RoundingMode.HALF_EVEN
                );
        for (int i = 0; i < expenseSaved.getQuantityInstallments(); i++) {
            Installment installment = createInstallment(expenseSaved, i, result);
            installmentRepository.save(installment);
        }
        return expenseSaved;
    }

    @Override
    public List<Expense> getExpenses(String search) {
        Specification<Expense> specification = Specification
                .where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        if(search != null) {
            specification = specification.and(ExpenseSpecification.titleLike(search));
        }
        return expenseRepository.findAll(specification);
    }

    @Override
    public Expense getById(UUID id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Despesa não encontrada"));
    }

    @Override
    public void delete(UUID id) {
        Expense expense = getById(id);
        installmentRepository.deleteInstallmentByExpense(expense);
        expenseRepository.delete(expense);
    }

    @Override
    public void update(Expense expense) {
        expenseRepository.save(expense);
    }

    private Installment createInstallment(Expense expenseSaved, int i, BigDecimal result) {
        Installment installment = new Installment();
        installment.setCurrentMonth(expenseSaved.getInitialDate().plusMonths(i));
        installment.setInstallmentNumber(i + 1);
        installment.setInstallmentValue(result);
        installment.setQuantityInstallments(expenseSaved.getQuantityInstallments());
        installment.setPaid(false);
        installment.setInitialDate(expenseSaved.getInitialDate());
        installment.setExpense(expenseSaved);
        return installment;
    }
}
