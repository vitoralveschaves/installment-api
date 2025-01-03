package com.application.api.installment.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "revenue")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "installments")
@EntityListeners(AuditingEntityListener.class)
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @Column(name = "total_value", nullable = false)
    private BigDecimal totalValue;

    @Column(name = "is_installment", nullable = false)
    private Boolean isInstallment;

    @Column(name = "quantity_installments")
    private Integer quantityInstallments;

    @Column(name = "initial_date", nullable = false)
    private LocalDate initialDate;

    @OneToMany(mappedBy = "revenue", fetch = FetchType.LAZY)
    private List<Installment> installments;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "user_id")
    private UUID userId;
}
