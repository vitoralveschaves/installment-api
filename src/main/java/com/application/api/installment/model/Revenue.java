package com.application.api.installment.model;

import com.application.api.installment.enums.RevenueType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "revenue")
@Getter
@Setter
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
public class Revenue extends BaseModel {

    @Column(unique = true)
    private String uuid;

    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @Column(name = "total_value", nullable = false)
    private BigDecimal totalValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "initial_date", nullable = false)
    private LocalDate initialDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RevenueType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Revenue() {
        this.uuid = "R-" + UUID.randomUUID();
    }
}
