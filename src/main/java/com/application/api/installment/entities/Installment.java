package com.application.api.installment.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "installment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "revenue")
public class Installment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "current_month", nullable = false)
    private LocalDate currentMonth;

    @Column(name = "installment_number", nullable = false)
    private Integer installmentNumber;

    @Column(name = "installment_value", nullable = false)
    private BigDecimal installmentValue;

    @Column(name = "quantity_installments")
    private Integer quantityInstallments;

    @Column(name = "is_paid", nullable = false)
    private boolean isPaid;

    @Column(name = "initial_date", nullable = false)
    private LocalDate initialDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revenue_id")
    private Revenue revenue;

    @Column(name = "user_id")
    private UUID userId;
}
