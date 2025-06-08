package com.application.api.installment.service.impl;

import com.application.api.installment.converter.ExpenseEntityConverter;
import com.application.api.installment.converter.ExpensePaginationResponseConverter;
import com.application.api.installment.converter.ExpenseResponseConverter;
import com.application.api.installment.dto.ExpenseRequestDto;
import com.application.api.installment.dto.ExpenseResponseDto;
import com.application.api.installment.dto.ExpenseUpdateDto;
import com.application.api.installment.dto.PaginationResponseDto;
import com.application.api.installment.exception.NotFoundException;
import com.application.api.installment.exception.NotNullException;
import com.application.api.installment.model.Expense;
import com.application.api.installment.model.Installment;
import com.application.api.installment.repository.ExpenseRepository;
import com.application.api.installment.repository.InstallmentRepository;
import com.application.api.installment.repository.UserRepository;
import com.application.api.installment.repository.specification.ExpenseSpecification;
import com.application.api.installment.security.SecurityService;
import com.application.api.installment.service.ExpenseService;
import com.application.api.installment.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
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
    private final UserRepository userRepository;
    private final PaginationUtils pageableUtils;
    private final ExpensePaginationResponseConverter expensePaginationResponseConverter;

    @Override
    @Transactional
    public ExpenseResponseDto createExpense(ExpenseRequestDto request) {

        if(Objects.isNull(request)) {
            LOGGER.error("stage=error method=ExpenseServiceImpl.createExpense message=Expense data cannot be null");
            throw new NotNullException("Expense data cannot be null");
        }

        LOGGER.info("stage=init method=ExpenseServiceImpl.createExpense request={}", request);

        var userAuth = securityService.getAuthenticationUser();

        var user = userRepository.findByEmail(userAuth.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Expense expense = expenseEntityConverter.apply(request, user);

        List<Installment> installmentList = getInstallments(expense);

        expense.setInstallments(installmentList);

        expenseRepository.save(expense);
        LOGGER.info("stage=info method=ExpenseServiceImpl.createExpense message=Expense saved expenseId={}",
                expense.getId());

        var response = expenseResponseConverter.apply(expense);

        LOGGER.info("stage=end method=ExpenseServiceImpl.createExpense response={}", response);
        return response;
    }

    @Override
    public PaginationResponseDto<ExpenseResponseDto> getExpenses(Integer page, Integer pageSize, String search) {

        LOGGER.info("stage=init method=ExpenseServiceImpl.getExpenses filterBy={}", search);

        Specification<Expense> specification = filterExpenses(search);

        Pageable pageable = pageableUtils.getPageable(page, pageSize);

        var expensesPage = expenseRepository.findAll(specification, pageable);

        var response = expensePaginationResponseConverter.apply(expensesPage);

        LOGGER.info("stage=end method=ExpenseServiceImpl.getExpenses message=Expenses filtered");

        return response;
    }

    @Override
    public ExpenseResponseDto getById(String id) {

        if(Objects.isNull(id)) {
            LOGGER.error("stage=error method=ExpenseServiceImpl.getById message=Expense id cannot be null");
            throw new NotNullException("Expense id cannot be null");
        }

        LOGGER.info("stage=init method=ExpenseServiceImpl.getById expenseId={}", id);

        Expense expense = expenseRepository.findByUuid(id)
                .orElseGet(() -> {
                    LOGGER.error("stage=error method=ExpenseServiceImpl.getById message=Expense not found");
                    throw new NotFoundException("Expense not found");
                });

        LOGGER.info("stage=end method=ExpenseServiceImpl.getById message=Expense fetched expense={}", expense);
        return expenseResponseConverter.apply(expense);
    }

    @Override
    public void delete(String id) {

        if(Objects.isNull(id)) {
            LOGGER.error("stage=error method=ExpenseServiceImpl.delete message=Expense id cannot be null");
            throw new NotNullException("Expense id cannot be null");
        }

        LOGGER.info("stage=init method=ExpenseServiceImpl.delete expenseId={}", id);

        Expense expense = expenseRepository.findByUuid(id)
                .orElseGet(() -> {
                    LOGGER.error("stage=error method=ExpenseServiceImpl.delete message=Expense not found");
                    throw new NotFoundException("Expense not found");
                });

        installmentRepository.deleteInstallmentByExpense(expense);
        expenseRepository.delete(expense);

        LOGGER.info("stage=end method=ExpenseServiceImpl.delete message=Expense deleted");
    }

    @Override
    public void update(String id, ExpenseUpdateDto request) {
        //TODO ainda falta implementar
        Expense expense = expenseRepository.findByUuid(id)
                .orElseThrow(() -> new NotFoundException("Despesa não encontrada"));
        expense.setTitle(request.getTitle());
        expenseRepository.save(expense);
    }

    private Specification<Expense> filterExpenses(String search) {
        Specification<Expense> specification = Specification
                .where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        specification = specification.and(ExpenseSpecification
                .byUserId(UUID.fromString(securityService.getAuthenticationUser().getUserId())));

        if(Objects.nonNull(search)) {
            specification = specification.and(ExpenseSpecification.titleLike(search));
        }
        return specification;
    }

    private List<Installment> getInstallments(Expense expense) {

        LOGGER.info("stage=init method=ExpenseServiceImpl.getInstallments expenseId={}", expense.getId());

        BigDecimal valueByMonth = getInstallmentValue(expense);

        BigDecimal brokenValue = getBrokenValue(expense, valueByMonth);

        List<Installment> installmentList = new ArrayList<>();

        for (int i = 0; i < expense.getQuantityInstallments(); i++) {

            var isAbleToAddBrokenValue = expense.getQuantityInstallments() == (i + 1) && Objects.nonNull(brokenValue);

            if(isAbleToAddBrokenValue) {
                valueByMonth = valueByMonth.add(brokenValue);
            }

            var installmentNumber = i + 1;

            Installment installment = createInstallment(expense, installmentNumber, valueByMonth);
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

    private Installment createInstallment(Expense expense, Integer installmentNumber, BigDecimal result) {
        return Installment.builder()
                .currentMonth(expense.getInitialDate().plusMonths(installmentNumber - 1))
                .installmentNumber(installmentNumber)
                .installmentValue(result)
                .quantityInstallments(expense.getQuantityInstallments())
                .isPaid(Boolean.FALSE)
                .initialDate(expense.getInitialDate())
                .expense(expense)
                .build();
    }
}
