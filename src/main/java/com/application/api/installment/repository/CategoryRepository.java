package com.application.api.installment.repository;

import com.application.api.installment.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByUuid(String uuid);
    Boolean existsByUuid(String uuid);
    void deleteByUuid(String uuid);
}
