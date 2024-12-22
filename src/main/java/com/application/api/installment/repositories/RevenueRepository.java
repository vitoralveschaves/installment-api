package com.application.api.installment.repositories;

import com.application.api.installment.entities.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, UUID> {
}
