package com.application.api.installment.repositories.specification;

import com.application.api.installment.entities.Expense;
import org.springframework.data.jpa.domain.Specification;

public class ExpenseSpecification {
    public static Specification<Expense> titleLike(String search) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.upper(root.get("title")), "%" + search.toUpperCase() + "%");
    }
}
