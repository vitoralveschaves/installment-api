package com.application.api.installment.services;

import com.application.api.installment.entities.Installment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InstallmentService {
    List<Installment> getInstallments(String month);
}
