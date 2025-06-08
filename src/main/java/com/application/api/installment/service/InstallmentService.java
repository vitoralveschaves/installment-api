package com.application.api.installment.service;

import com.application.api.installment.dto.InstallmentBalanceResponseDto;
import com.application.api.installment.dto.InstallmentResponseDto;
import com.application.api.installment.dto.PaginationResponseDto;

public interface InstallmentService {
    PaginationResponseDto<InstallmentResponseDto> getInstallments(
            String month,
            String year,
            Integer page,
            Integer pageSize,
            String search,
            String category
    );
    InstallmentBalanceResponseDto getInstallmentBalance(String month);
    void pay(String id);
}
