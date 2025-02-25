package com.application.api.installment.services.impl;

import com.application.api.installment.dto.ExpenseRequestDto;
import com.application.api.installment.dto.ExpenseResponseDto;
import com.application.api.installment.dto.ExpenseUpdateDto;
import com.application.api.installment.entities.Category;
import com.application.api.installment.entities.Expense;
import com.application.api.installment.entities.Installment;
import com.application.api.installment.exceptions.NotFoundException;
import com.application.api.installment.repositories.ExpenseRepository;
import com.application.api.installment.repositories.InstallmentRepository;
import com.application.api.installment.repositories.specification.ExpenseSpecification;
import com.application.api.installment.security.SecurityService;
import com.application.api.installment.services.CategoryService;
import com.application.api.installment.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final InstallmentRepository installmentRepository;
    private final CategoryService categoryService;
    private final SecurityService securityService;

    @Override
    @Transactional
    public Expense createExpense(ExpenseRequestDto request) {
        Optional<Category> category = categoryService.getById(request.categoryId());
        Expense expense = request.toEntity();
        category.ifPresent(expense::setCategory);
        expense.setUser(securityService.getAuthenticationUser());

        Expense expenseSaved = expenseRepository.save(expense);
        var result = expenseSaved
                .getTotalValue()
                .divide(
                        BigDecimal.valueOf(expenseSaved.getQuantityInstallments()),
                        3,
                        RoundingMode.HALF_EVEN
                );
        List<Installment> installmentList = new ArrayList<>();
        for (int i = 0; i < expenseSaved.getQuantityInstallments(); i++) {
            Installment installment = createInstallment(expenseSaved, i, result);
            installmentList.add(installment);
        }
        installmentRepository.saveAll(installmentList);
        return expenseSaved;
    }

    @Override
    public List<ExpenseResponseDto> getExpenses(String search) {
        Specification<Expense> specification = Specification
                .where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        if(search != null) {
            specification = specification.and(ExpenseSpecification.titleLike(search));
        }
        List<Expense> expensesList = expenseRepository.findAll(specification);
        return expensesList.stream().map(ExpenseResponseDto::new).toList();
    }

    @Override
    public ExpenseResponseDto getById(UUID id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Despesa não encontrada"));
        return new ExpenseResponseDto(expense);
    }

    @Override
    public void delete(UUID id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Despesa não encontrada"));
        installmentRepository.deleteInstallmentByExpense(expense);
        expenseRepository.delete(expense);
    }

    @Override
    public void update(UUID id, ExpenseUpdateDto request) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Despesa não encontrada"));
        expense.setTitle(request.title());
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
