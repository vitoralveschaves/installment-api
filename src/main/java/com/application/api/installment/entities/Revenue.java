package com.application.api.installment.entities;

import com.application.api.installment.entities.enums.RevenueType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "revenue")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Revenue extends Transaction {
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RevenueType type;
}
