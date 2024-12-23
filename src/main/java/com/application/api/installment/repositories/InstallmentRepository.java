package com.application.api.installment.repositories;

import com.application.api.installment.entities.Installment;
import com.application.api.installment.entities.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface InstallmentRepository extends JpaRepository<Installment, UUID> {

    @Modifying
    @Transactional
    @Query("delete from Installment as i where i.revenue = :revenue")
    void deleteInstallmentByRevenue(@Param("revenue") Revenue revenue);
}
