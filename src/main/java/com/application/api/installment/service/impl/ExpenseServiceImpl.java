package com.application.api.installment.service.impl;

import com.application.api.installment.converter.ExpenseEntityConverter;
import com.application.api.installment.converter.ExpenseResponseConverter;
import com.application.api.installment.dto.ExpenseRequestDto;
import com.application.api.installment.dto.ExpenseResponseDto;
import com.application.api.installment.dto.ExpenseUpdateDto;
import com.application.api.installment.exception.NotFoundException;
import com.application.api.installment.model.Expense;
import com.application.api.installment.model.Installment;
import com.application.api.installment.model.User;
import com.application.api.installment.repository.ExpenseRepository;
import com.application.api.installment.repository.InstallmentRepository;
import com.application.api.installment.repository.specification.ExpenseSpecification;
import com.application.api.installment.security.SecurityService;
import com.application.api.installment.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseServiceImpl.class);
    private final ExpenseRepository expenseRepository;
    private final InstallmentRepository installmentRepository;
    private final SecurityService securityService;
    private final ExpenseEntityConverter expenseEntityConverter;
    private final ExpenseResponseConverter expenseResponseConverter;

    @Override
    @Transactional
    public ExpenseResponseDto createExpense(ExpenseRequestDto request) {

        if(Objects.isNull(request)) {
            LOGGER.error("stage=error method=ExpenseServiceImpl.createExpense message=Expense data cannot be null");
            throw new RuntimeException("Expense data cannot be null");
        }

        LOGGER.info("stage=init method=ExpenseServiceImpl.createExpense request={}", request);

        User userAuth = securityService.getAuthenticationUser();
        Expense expense = expenseEntityConverter.apply(request, userAuth);

        expenseRepository.save(expense);
        LOGGER.info("stage=info method=ExpenseServiceImpl.createExpense message=Expense saved expenseId={}",
                expense.getId());

        List<Installment> installmentList = getInstallments(expense);

        installmentRepository.saveAll(installmentList);
        var response = expenseResponseConverter.apply(expense);

        LOGGER.info("stage=end method=ExpenseServiceImpl.createExpense response={}", response);
        return response;
    }

    @Override
    public List<ExpenseResponseDto> getExpenses(String search) {

        LOGGER.info("stage=init method=ExpenseServiceImpl.getExpenses filterBy={}", search);

        Specification<Expense> specification = Specification
                .where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if(search != null) {
            specification = specification.and(ExpenseSpecification.titleLike(search));
        }

        List<Expense> expensesList = expenseRepository.findAll(specification);

        var expenseResponseList = expensesList.stream().map(expenseResponseConverter).toList();

        LOGGER.info("stage=end method=ExpenseServiceImpl.getExpenses message=Expenses filtered");

        return expenseResponseList;
    }

    @Override
    public ExpenseResponseDto getById(UUID id) {

        if(Objects.isNull(id)) {
            LOGGER.error("stage=error method=ExpenseServiceImpl.getById message=Expense id cannot be null");
            throw new RuntimeException("Expense id cannot be null");
        }

        LOGGER.info("stage=init method=ExpenseServiceImpl.getById expenseId={}", id);

        Expense expense = expenseRepository.findById(id)
                .orElseGet(() -> {
                    LOGGER.error("stage=error method=ExpenseServiceImpl.getById message=Expense not found");
                    throw new NotFoundException("Expense not found");
                });

        LOGGER.info("stage=end method=ExpenseServiceImpl.getById message=Expense fetched expense={}", expense);
        return expenseResponseConverter.apply(expense);
    }

    @Override
    public void delete(UUID id) {

        if(Objects.isNull(id)) {
            LOGGER.error("stage=error method=ExpenseServiceImpl.delete message=Expense id cannot be null");
            throw new RuntimeException("Expense id cannot be null");
        }

        LOGGER.info("stage=init method=ExpenseServiceImpl.delete expenseId={}", id);

        Expense expense = expenseRepository.findById(id)
                .orElseGet(() -> {
                    LOGGER.error("stage=error method=ExpenseServiceImpl.delete message=Expense not found");
                    throw new NotFoundException("Expense not found");
                });

        installmentRepository.deleteInstallmentByExpense(expense);
        expenseRepository.delete(expense);

        LOGGER.info("stage=end method=ExpenseServiceImpl.delete message=Expense deleted");
    }

    @Override
    public void update(UUID id, ExpenseUpdateDto request) {
        //TODO ainda falta implementar
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Despesa não encontrada"));
        expense.setTitle(request.getTitle());
        expenseRepository.save(expense);
    }

    private List<Installment> getInstallments(Expense expense) {

        LOGGER.info("stage=init method=ExpenseServiceImpl.getInstallments expenseId={}", expense.getId());

        BigDecimal valueByMonth = getInstallmentValue(expense);

        BigDecimal brokenValue = getBrokenValue(expense, valueByMonth);

        List<Installment> installmentList = new ArrayList<>();

        for (int i = 0; i < expense.getQuantityInstallments(); i++) {

            if(expense.getQuantityInstallments() == (i + 1) && Objects.nonNull(brokenValue)) {
                valueByMonth = valueByMonth.add(brokenValue);
            }

            Installment installment = createInstallment(expense, i, valueByMonth);
            installmentList.add(installment);
        }

        LOGGER.info("stage=end method=ExpenseServiceImpl.getInstallments expenseId={}", expense.getId());

        return installmentList;
    }

    private BigDecimal getInstallmentValue(Expense expense) {
        return expense.getTotalValue()
                .divide(BigDecimal.valueOf(expense.getQuantityInstallments()),2, RoundingMode.DOWN);
    }

    private BigDecimal getBrokenValue(Expense expense, BigDecimal valueByMonth) {

        LOGGER.info("stage=init method=ExpenseServiceImpl.getBreakValue");

        var totalValue = expense.getTotalValue();
        var quantityInstallments = expense.getQuantityInstallments();

        BigDecimal valueMultiply = valueByMonth.multiply(BigDecimal.valueOf(quantityInstallments));

        if(valueMultiply.compareTo(totalValue) == 0) {
            LOGGER.info("stage=end method=ExpenseServiceImpl.getBreakValue breakValue={}", BigDecimal.ZERO);
            return null;
        }

        BigDecimal brokenValue = totalValue.subtract(valueMultiply);

        LOGGER.info("stage=end method=ExpenseServiceImpl.getBreakValue breakValue={}", brokenValue);
        return brokenValue;
    }

    private Installment createInstallment(Expense expense, int i, BigDecimal result) {
        return Installment.builder()
                .currentMonth(expense.getInitialDate().plusMonths(i))
                .installmentNumber(i + 1)
                .installmentValue(result)
                .quantityInstallments(expense.getQuantityInstallments())
                .isPaid(false)
                .initialDate(expense.getInitialDate())
                .expense(expense)
                .build();
    }
}
