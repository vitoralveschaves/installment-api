package com.application.api.installment.repository;

import com.application.api.installment.model.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RevenueRepository extends JpaRepository<Revenue, Long> {
}
