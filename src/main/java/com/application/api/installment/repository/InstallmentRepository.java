package com.application.api.installment.repository;

import com.application.api.installment.model.Installment;
import com.application.api.installment.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface InstallmentRepository extends JpaRepository<Installment, Long>,
        JpaSpecificationExecutor<Installment> {
    @Modifying
    @Transactional
    @Query("delete from Installment as i where i.expense = :expense")
    void deleteInstallmentByExpense(@Param("expense") Expense expense);

    @Query("""
        SELECT SUM(CASE WHEN FUNCTION('to_char', i.currentMonth, 'MM') = :month
        THEN i.installmentValue ELSE 0 END),
        SUM(i.installmentValue)
        FROM Installment i
        JOIN i.expense e
        WHERE e.user.uuid = :userId
        AND i.isPaid = false
    """)
    List<Object[]> getAllInstallmentsValueByUserIdAndMonth(String userId, String month);

    @Query("""
        SELECT SUM(i.installmentValue) FROM Installment i
        JOIN i.expense e JOIN e.user u
        WHERE u.uuid = :userId AND i.isPaid = false
    """)
    BigDecimal getAllInstallmentsValueByUserId(String userId);

    @Query("SELECT i FROM Installment i WHERE i.isPaid = false AND i.uuid = :installmentId")
    Optional<Installment> findInstalmentNotPaidById(@Param("installmentId") String installmentId);

    @Query("SELECT i FROM Installment i JOIN i.expense e WHERE e.id = :id AND i.installmentNumber = :installmentNumber")
    Optional<Installment> findByExpenseIdAndInstallmentNumber(Long id, int installmentNumber);
}
