package com.application.api.installment.services;

import com.application.api.installment.entities.Revenue;

import java.util.UUID;

public interface RevenueService {
    Revenue createRevenue(Revenue revenue);
    Revenue getById(UUID id);
    void delete(UUID id);
    void update(Revenue revenue);
}
