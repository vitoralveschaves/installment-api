package com.application.api.installment.service;

import com.application.api.installment.dto.InstallmentBalanceResponseDto;
import com.application.api.installment.dto.InstallmentResponseDto;
import com.application.api.installment.model.Installment;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface InstallmentService {
    Page<InstallmentResponseDto> getInstallments(
            String month,
            String year,
            Integer page,
            Integer pageSize,
            String search,
            String category
    );
    InstallmentBalanceResponseDto getInstallmentBalance(String month);
    void pay(UUID id);
}
