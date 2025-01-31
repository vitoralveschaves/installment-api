package com.application.api.installment.services;

import com.application.api.installment.entities.Installment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InstallmentService {
    Page<Installment> getInstallments(String month, String year, Integer page, Integer pageSize);
}
