package com.application.api.installment.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "expense")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "installments")
public class Expense extends Transaction{
    @Column(name = "quantity_installments")
    private Integer quantityInstallments;
    @OneToMany(mappedBy = "expense", fetch = FetchType.LAZY)
    private List<Installment> installments;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
