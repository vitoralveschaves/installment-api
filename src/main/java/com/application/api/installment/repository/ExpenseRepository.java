package com.application.api.installment.repository;

import com.application.api.installment.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, Long>,
        JpaSpecificationExecutor<Expense> {

    Optional<Expense> findByUuid(String uuid);

    @Query("""
        SELECT SUM(i.installmentValue) FROM Installment i
        JOIN i.expense e JOIN e.user u
        WHERE i.isPaid = false AND u.id = :userId
    """)
    BigDecimal getAllInstallmentsValuePaidByUserId(UUID userId);
}
