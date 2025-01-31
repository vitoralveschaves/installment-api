package com.application.api.installment.services.impl;

import com.application.api.installment.entities.Installment;
import com.application.api.installment.entities.Revenue;
import com.application.api.installment.exceptions.RevenueNotFoundException;
import com.application.api.installment.repositories.InstallmentRepository;
import com.application.api.installment.repositories.RevenueRepository;
import com.application.api.installment.services.RevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RevenueServiceImpl implements RevenueService {

    private final RevenueRepository revenueRepository;
    private final InstallmentRepository installmentRepository;

    @Override
    @Transactional
    public Revenue createRevenue(Revenue revenue) {
        Revenue revenueSaved = revenueRepository.save(revenue);
        var result = revenueSaved
                .getTotalValue()
                .divide(
                        BigDecimal.valueOf(revenueSaved.getQuantityInstallments()),
                        3,
                        RoundingMode.HALF_EVEN
                );
        for (int i = 0; i < revenueSaved.getQuantityInstallments(); i++) {
            Installment installment = createInstallment(revenueSaved, i, result);
            installmentRepository.save(installment);
        }
        return revenueSaved;
    }

    @Override
    public Revenue getById(UUID id) {
        return revenueRepository.findById(id)
                .orElseThrow(() -> new RevenueNotFoundException("Despesa não encontrada"));
    }

    @Override
    public void delete(UUID id) {
        Revenue revenue = getById(id);
        installmentRepository.deleteInstallmentByRevenue(revenue);
        revenueRepository.delete(revenue);
    }

    @Override
    public void update(Revenue revenue) {
        revenueRepository.save(revenue);
    }

    private Installment createInstallment(Revenue revenueSaved, int i, BigDecimal result) {
        Installment installment = new Installment();
        installment.setCurrentMonth(revenueSaved.getInitialDate().plusMonths(i));
        installment.setInstallmentNumber(i + 1);
        installment.setInstallmentValue(result);
        installment.setQuantityInstallments(revenueSaved.getQuantityInstallments());
        installment.setPaid(false);
        installment.setInitialDate(revenueSaved.getInitialDate());
        installment.setRevenue(revenueSaved);
        return installment;
    }
}
