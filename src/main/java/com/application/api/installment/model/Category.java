package com.application.api.installment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@ToString(exclude = {"expenses", "revenues"})
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class Category extends BaseModel {

    @Column(unique = true)
    private String uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Expense> expenses;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Revenue> revenues;

    public Category() {
        this.uuid = "CAT-" + UUID.randomUUID();
    }
}
