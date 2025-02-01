package com.application.api.installment.services;

import com.application.api.installment.entities.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {
    Category createCategory(Category category);
    List<Category> getCategories();
    Optional<Category> getById(UUID id);
    void deleteCategory(UUID id);
}
