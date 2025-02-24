package com.application.api.installment.services;

import com.application.api.installment.dto.CategoryRequestDto;
import com.application.api.installment.dto.CategoryResponseDto;
import com.application.api.installment.entities.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto request);
    List<CategoryResponseDto> getCategories();
    Optional<Category> getById(UUID id);
    void deleteCategory(UUID id);
}
