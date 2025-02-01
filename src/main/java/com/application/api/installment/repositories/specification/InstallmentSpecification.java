package com.application.api.installment.repositories.specification;

import com.application.api.installment.entities.Installment;
import com.application.api.installment.entities.Revenue;
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
                .like(criteriaBuilder.upper(root.get("revenue").get("title")), "%" + search.toUpperCase() + "%");
    }
}
