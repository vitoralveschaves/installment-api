package com.application.api.installment.services.impl;

import com.application.api.installment.entities.Installment;
import com.application.api.installment.repositories.InstallmentRepository;
import com.application.api.installment.repositories.specification.InstallmentSpecification;
import com.application.api.installment.services.InstallmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstallmentServiceImpl implements InstallmentService {

    private final InstallmentRepository installmentRepository;

    @Override
    public List<Installment> getInstallments(String month) {
        Specification<Installment> specification = Specification
                .where(InstallmentSpecification.getByMonth(month));
        return installmentRepository.findAll(specification);
    }
}
