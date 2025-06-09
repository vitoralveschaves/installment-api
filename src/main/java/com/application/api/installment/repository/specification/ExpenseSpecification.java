package com.application.api.installment.repository.specification;

import com.application.api.installment.model.Expense;
import org.springframework.data.jpa.domain.Specification;

public class ExpenseSpecification {

    public static Specification<Expense> titleLike(String search) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.upper(root.get("title")), "%" + search.toUpperCase() + "%");
    }

    public static Specification<Expense> byUserId(String id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("user").get("uuid"), id);
    }
}
