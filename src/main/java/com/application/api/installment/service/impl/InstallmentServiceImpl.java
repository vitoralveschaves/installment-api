package com.application.api.installment.service.impl;

import com.application.api.installment.converter.BalanceResponseConverter;
import com.application.api.installment.converter.InstallmentPaginationResponseConverter;
import com.application.api.installment.dto.InstallmentBalanceResponseDto;
import com.application.api.installment.dto.InstallmentResponseDto;
import com.application.api.installment.dto.PaginationResponseDto;
import com.application.api.installment.dto.UserAuthenticationData;
import com.application.api.installment.exception.NotFoundException;
import com.application.api.installment.model.Installment;
import com.application.api.installment.repository.InstallmentRepository;
import com.application.api.installment.repository.specification.InstallmentSpecification;
import com.application.api.installment.security.SecurityService;
import com.application.api.installment.service.InstallmentService;
import com.application.api.installment.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InstallmentServiceImpl implements InstallmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstallmentServiceImpl.class);
    private final InstallmentRepository installmentRepository;
    private final SecurityService securityService;
    private final InstallmentPaginationResponseConverter installmentPaginationResponseConverter;
    private final PaginationUtils paginationUtils;
    private final BalanceResponseConverter balanceResponseConverter;

    @Override
    public PaginationResponseDto<InstallmentResponseDto> getInstallments(
            String month,String year, Integer page,Integer pageSize, String search, String category) {

        LOGGER.info("stage=init method=InstallmentServiceImpl.getInstallments");

        var specification = filterInstallments(month, year, search, category);

        Pageable pageable = paginationUtils.getPageable(page, pageSize);

        var installments = installmentRepository.findAll(specification, pageable);

        PaginationResponseDto<InstallmentResponseDto> response = installmentPaginationResponseConverter.apply(installments);

        LOGGER.info("stage=end method=InstallmentServiceImpl.getInstallments");
        return response;
    }

    @Override
    public InstallmentBalanceResponseDto getInstallmentBalance(String month) {

        LOGGER.info("stage=init method=InstallmentServiceImpl.getInstallmentBalance");

        var user = securityService.getAuthenticationUser();

        var response = getInstallmentsBalanceValue(user, month);

        LOGGER.info("stage=end method=InstallmentServiceImpl.getInstallmentBalance dto={}", response);
        return response;
    }

    @Override
    @Transactional
    public void pay(String id) {

        Installment installment = installmentRepository.findInstalmentNotPaidById(id).orElseGet(() -> {
            LOGGER.error("stage=error method=InstallmentServiceImpl.pay message=This installment does not exist or has already been paid");
            throw new NotFoundException("This installment does not exist or has already been paid");
        });

        LOGGER.info("stage=init method=InstallmentServiceImpl.pay installmentId={}", id);

        isAbleToPay(installment);
        installment.setPaid(Boolean.TRUE);

        installmentRepository.save(installment);
        LOGGER.info("stage=end method=InstallmentServiceImpl.pay installmentId={}", id);
    }

    private void isAbleToPay(Installment installment) {

        LOGGER.info("stage=init method=InstallmentServiceImpl.isAbleToPay installmentId={}", installment.getId());

        var installmentBefore = installmentRepository
                .findByExpenseIdAndInstallmentNumber(installment.getExpense().getId(),
                        installment.getInstallmentNumber() - 1);

        if(installmentBefore.isEmpty()) {
            LOGGER.info("stage=end method=InstallmentServiceImpl.isAbleToPay installmentId={}", installment.getId());
            return;
        }

        if(!installmentBefore.get().isPaid()) {
            LOGGER.error("stage=error method=InstallmentServiceImpl.isAbleToPay message=To pay this installment, you must pay the previous installment");
            throw new RuntimeException("To pay this installment, you must pay the previous installment");
        }

        LOGGER.info("stage=end method=InstallmentServiceImpl.isAbleToPay installmentId={}", installment.getId());
    }

    private InstallmentBalanceResponseDto getInstallmentsBalanceValue(UserAuthenticationData user, String month) {

        LOGGER.info("stage=init method=InstallmentServiceImpl.getInstallmentsBalanceValue");

        if(Objects.nonNull(month)) {
            var balances = installmentRepository.getAllInstallmentsValueByUserIdAndMonth(user.getUserId(), month);

            LOGGER.info("stage=end method=InstallmentServiceImpl.getInstallmentsBalanceValue");

            return balanceResponseConverter.apply(balances);
        }

        var totalBalance = installmentRepository.getAllInstallmentsValueByUserId(user.getUserId());

        LOGGER.info("stage=end method=InstallmentServiceImpl.getInstallmentsBalanceValue");

        return InstallmentBalanceResponseDto.builder()
                .totalToPayByMonth(BigDecimal.ZERO)
                .totalToPay(totalBalance == null ? BigDecimal.ZERO : totalBalance)
                .build();
    }

    private Specification<Installment> filterInstallments(String month,String year, String search, String category) {

        Specification<Installment> specification = Specification
                .where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        specification = specification.and(InstallmentSpecification
                .byUserId(securityService.getAuthenticationUser().getUserId()));

        if(Objects.nonNull(year)) {
            specification = specification.and(InstallmentSpecification.getByYear(year));
        }

        if(Objects.nonNull(month)) {
            specification = specification.and(InstallmentSpecification.getByMonth(month));
        }

        if(Objects.nonNull(search)) {
            specification = specification.and(InstallmentSpecification.titleLike(search));
        }

        if(Objects.nonNull(category)) {
            specification = specification.and(InstallmentSpecification.categoryEquals(category));
        }

        return specification;
    }
}