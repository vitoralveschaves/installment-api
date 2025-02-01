package com.application.api.installment.services;

import com.application.api.installment.entities.Revenue;

import java.util.List;
import java.util.UUID;

public interface RevenueService {
    Revenue createRevenue(Revenue revenue);
    List<Revenue> getRevenues(String search);
    Revenue getById(UUID id);
    void delete(UUID id);
    void update(Revenue revenue);
}
