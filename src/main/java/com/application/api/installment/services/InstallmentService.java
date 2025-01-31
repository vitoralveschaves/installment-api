package com.application.api.installment.services;

import com.application.api.installment.entities.Installment;

import java.util.List;

public interface InstallmentService {
    List<Installment> getInstallments(String month, String year);
}
