package com.application.api.installment.service.impl;

import com.application.api.installment.converter.InstallmentPaginationResponseConverter;
import com.application.api.installment.dto.InstallmentBalanceResponseDto;
import com.application.api.installment.dto.InstallmentResponseDto;
import com.application.api.installment.dto.PaginationResponseDto;
import com.application.api.installment.exception.NotFoundException;
import com.application.api.installment.model.Installment;
import com.application.api.installment.model.User;
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
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstallmentServiceImpl implements InstallmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstallmentServiceImpl.class);
    private final InstallmentRepository installmentRepository;
    private final SecurityService securityService;
    private final InstallmentPaginationResponseConverter installmentPaginationResponseConverter;
    private final PaginationUtils paginationUtils;

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

        BigDecimal installmentsBalanceValue = Optional
                .ofNullable(getInstallmentsBalanceValue(user, month))
                .orElse(BigDecimal.ZERO);

        var response = InstallmentBalanceResponseDto.builder()
                .totalToPay(installmentsBalanceValue)
                .build();

        LOGGER.info("stage=end method=InstallmentServiceImpl.getInstallmentBalance dto={}", response);
        return response;
    }

    @Override
    @Transactional
    public void pay(UUID id) {

        Installment installment = installmentRepository.findInstalmentNotPaidById(id).orElseGet(() -> {
            LOGGER.error("stage=error method=InstallmentServiceImpl.pay message=This installment does not exist or has already been paid");
            throw new NotFoundException("This installment does not exist or has already been paid");
        });

        LOGGER.info("stage=init method=InstallmentServiceImpl.pay installmentId={}", id);

        isAbleToPay(installment);
        installment.setPaid(true);

        installmentRepository.save(installment);
        LOGGER.info("stage=end method=InstallmentServiceImpl.pay installmentId={}", id);
    }

    private void isAbleToPay(Installment installment) {

        LOGGER.info("stage=init method=InstallmentServiceImpl.isAbleToPay installmentId={}", installment.getId());

        var installmentBefore = installmentRepository
                .findByExpenseIdAndInstallmentNumber(installment.getExpense().getId(),
                        installment.getInstallmentNumber() - 1);

        if(installmentBefore.isEmpty()) {
            return;
        }

        if(!installmentBefore.get().isPaid()) {
            LOGGER.error("stage=error method=InstallmentServiceImpl.isAbleToPay message=To pay this installment, you must pay the previous installment");
            throw new RuntimeException("To pay this installment, you must pay the previous installment");
        }
    }

    private BigDecimal getInstallmentsBalanceValue(User user, String month) {
        if(Objects.nonNull(month)) {
            return installmentRepository.getAllInstallmentsValueByUserIdAndMonth(user.getId(), month);
        } else {
            return installmentRepository.getAllInstallmentsValueByUserId(user.getId());
        }
    }

    private Specification<Installment> filterInstallments(String month,String year, String search, String category) {

        Specification<Installment> specification = Specification
                .where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        specification = specification.and(InstallmentSpecification
                .byUserId(securityService.getAuthenticationUser().getId()));

        if(year != null) {
            specification = specification.and(InstallmentSpecification.getByYear(year));
        }
        if(month != null) {
            specification = specification.and(InstallmentSpecification.getByMonth(month));
        }
        if(search != null) {
            specification = specification.and(InstallmentSpecification.titleLike(search));
        }
        if(category != null) {
            specification = specification.and(InstallmentSpecification.categoryEquals(category));
        }
        return specification;
    }
}