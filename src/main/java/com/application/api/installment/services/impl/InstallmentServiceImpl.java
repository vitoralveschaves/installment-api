package com.application.api.installment.services.impl;

import com.application.api.installment.entities.Installment;
import com.application.api.installment.repositories.InstallmentRepository;
import com.application.api.installment.repositories.specification.InstallmentSpecification;
import com.application.api.installment.services.InstallmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstallmentServiceImpl implements InstallmentService {

    private final InstallmentRepository installmentRepository;

    @Override
    public Page<Installment> getInstallments(String month, String year, Integer page, Integer pageSize) {
        Specification<Installment> specification = Specification
                .where(InstallmentSpecification.getByMonth(month))
                .and(InstallmentSpecification.getByYear(year));
        Pageable pageable = PageRequest.of(page, pageSize);
        return installmentRepository.findAll(specification, pageable);
    }
}
