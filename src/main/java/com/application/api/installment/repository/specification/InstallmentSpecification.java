package com.application.api.installment.repository.specification;

import com.application.api.installment.model.Expense;
import com.application.api.installment.model.Installment;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class InstallmentSpecification {

    public static Specification<Installment> getByMonth(String month) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                criteriaBuilder.function(
                        "to_char",
                        String.class,
                        root.get("currentMonth"),
                        criteriaBuilder.literal("MM")
                ), month
        );
    }

    public static Specification<Installment> getByYear(String year) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                criteriaBuilder.function(
                        "to_char",
                        String.class,
                        root.get("currentMonth"),
                        criteriaBuilder.literal("YYYY")
                ), year
        );
    }

    public static Specification<Installment> titleLike(String search) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.upper(root.get("expense").get("title")), "%" + search.toUpperCase() + "%");
    }

    public static Specification<Installment> categoryEquals(String category) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(criteriaBuilder.upper(root.get("expense").get("category").get("name")), category.toUpperCase());
    }

    public static Specification<Installment> byUserId(String id) {
        return (root, query, criteriaBuilder) -> {
            Join<Installment, Expense> expenseJoin = root.join("expense");
            return criteriaBuilder.equal(expenseJoin.get("user").get("uuid"), id);
        };
    }
}
