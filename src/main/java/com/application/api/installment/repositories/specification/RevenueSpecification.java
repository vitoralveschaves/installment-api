package com.application.api.installment.repositories.specification;

import com.application.api.installment.entities.Revenue;
import org.springframework.data.jpa.domain.Specification;

public class RevenueSpecification {
    public static Specification<Revenue> titleLike(String search) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.upper(root.get("title")), "%" + search.toUpperCase() + "%");
    }
}
