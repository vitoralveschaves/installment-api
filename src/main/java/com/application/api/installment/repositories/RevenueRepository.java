package com.application.api.installment.repositories;

import com.application.api.installment.entities.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RevenueRepository extends JpaRepository<Revenue, UUID> {
}
