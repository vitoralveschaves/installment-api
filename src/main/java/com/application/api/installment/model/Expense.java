package com.application.api.installment.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "expense")
@Getter
@Setter
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = "installments")
@SuperBuilder
public class Expense extends BaseModel {

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

    @Column(name = "quantity_installments")
    private Integer quantityInstallments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expense", fetch = FetchType.LAZY)
    private List<Installment> installments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Expense() {
        this.uuid = "E-" + UUID.randomUUID();
    }
}
