package com.application.api.installment.service;

import com.application.api.installment.dto.CategoryRequestDto;
import com.application.api.installment.dto.CategoryResponseDto;
import com.application.api.installment.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto request);
    List<CategoryResponseDto> getCategories();
    CategoryResponseDto getByUuid(String id);
    Optional<Category> getById(String id);
    void deleteCategory(String id);
}
