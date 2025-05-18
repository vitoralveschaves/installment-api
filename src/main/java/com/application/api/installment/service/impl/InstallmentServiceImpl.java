package com.application.api.installment.service.impl;

import com.application.api.installment.converter.InstallmentResponseConverter;
import com.application.api.installment.dto.InstallmentResponseDto;
import com.application.api.installment.model.Installment;
import com.application.api.installment.repository.InstallmentRepository;
import com.application.api.installment.repository.specification.InstallmentSpecification;
import com.application.api.installment.security.SecurityService;
import com.application.api.installment.service.InstallmentService;
import com.application.api.installment.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstallmentServiceImpl implements InstallmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstallmentServiceImpl.class);
    private final InstallmentRepository installmentRepository;
    private final SecurityService securityService;
    private final InstallmentResponseConverter installmentResponseConverter;
    private final PaginationUtils paginationUtils;

    @Override
    public Page<InstallmentResponseDto> getInstallments(
            String month,String year, Integer page,Integer pageSize, String search, String category) {

        LOGGER.info("stage=init method=InstallmentServiceImpl.getInstallments");

        var specification = filter(month, year, search, category);

        Pageable pageable = paginationUtils.getPageable(page, pageSize);

        var installments = installmentRepository.findAll(specification, pageable);

        Page<InstallmentResponseDto> response = installments.map(installmentResponseConverter);

        LOGGER.info("stage=end method=InstallmentServiceImpl.getInstallments");

        return response;
    }

    private Specification<Installment> filter(String month,String year, String search, String category) {

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
