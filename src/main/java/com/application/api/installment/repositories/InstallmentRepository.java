package com.application.api.installment.repositories;

import com.application.api.installment.entities.Installment;
import com.application.api.installment.entities.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface InstallmentRepository extends JpaRepository<Installment, UUID>,
        JpaSpecificationExecutor<Installment> {
    @Modifying
    @Transactional
    @Query("delete from Installment as i where i.expense = :expense")
    void deleteInstallmentByExpense(@Param("expense") Expense expense);

    @Query("SELECT i FROM Installment i JOIN i.expense e WHERE e.user.id = :userId")
    Page<Installment> findAllByUser(@Param("userId") UUID userId, Pageable pageable);
}
