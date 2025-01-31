package com.application.api.installment.repositories.specification;

import com.application.api.installment.entities.Installment;
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
}
