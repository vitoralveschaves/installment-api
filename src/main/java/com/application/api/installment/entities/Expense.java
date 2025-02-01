package com.application.api.installment.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "expense")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "installments")
@EntityListeners(AuditingEntityListener.class)
public class Expense extends Transaction{
    @Column(name = "quantity_installments")
    private Integer quantityInstallments;
    @OneToMany(mappedBy = "expense", fetch = FetchType.LAZY)
    private List<Installment> installments;
}
