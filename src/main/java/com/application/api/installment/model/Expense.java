package com.application.api.installment.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "expense")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "installments")
@SuperBuilder
public class Expense extends Transaction{
    @Column(name = "quantity_installments")
    private Integer quantityInstallments;
    @OneToMany(mappedBy = "expense", fetch = FetchType.LAZY)
    private List<Installment> installments;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
