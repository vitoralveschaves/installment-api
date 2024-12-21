package com.application.api.installment.repositories;

import com.application.api.installment.entities.Installment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstallmentRepository extends JpaRepository<Installment, UUID> {
}
