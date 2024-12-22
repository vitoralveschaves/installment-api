package com.application.api.installment.services;

import com.application.api.installment.entities.Installment;
import com.application.api.installment.entities.Revenue;
import com.application.api.installment.repositories.InstallmentRepository;
import com.application.api.installment.repositories.RevenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RevenueService {

    private final RevenueRepository revenueRepository;
    private final InstallmentRepository installmentRepository;

    @Transactional
    public Revenue createRevenue(Revenue revenue) {

        if(revenue.getQuantityInstallments() == 1) {
            revenue.setQuantityInstallments(0);
        }
        Revenue revenueSaved = revenueRepository.save(revenue);

        if (revenue.getIsInstallment() && revenue.getQuantityInstallments() > 1) {
            var result = revenueSaved.getTotalValue().divide(BigDecimal.valueOf(revenueSaved.getQuantityInstallments()));
            for (int i = 0; i < revenueSaved.getQuantityInstallments(); i++) {
                Installment installment = createInstallment(revenueSaved, i, result);
                installmentRepository.save(installment);
            }
        }
        return revenueSaved;
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
