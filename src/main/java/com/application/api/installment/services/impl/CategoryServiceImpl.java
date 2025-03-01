package com.application.api.installment.services.impl;

import com.application.api.installment.dto.CategoryRequestDto;
import com.application.api.installment.dto.CategoryResponseDto;
import com.application.api.installment.entities.Category;
import com.application.api.installment.exceptions.NotFoundException;
import com.application.api.installment.repositories.CategoryRepository;
import com.application.api.installment.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto request) {
        Category category = request.toEntity();
        categoryRepository.save(category);
        return new CategoryResponseDto(category.getId(), category.getName());
    }

    @Override
    public List<CategoryResponseDto> getCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(CategoryResponseDto::new).toList();
    }

    @Override
    public Optional<Category> getById(UUID id) {
        if(id != null) {
            return categoryRepository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public void deleteCategory(UUID id) {
        if(!categoryRepository.existsById(id)) {
            throw new NotFoundException("Categoria não encontrada");
        }
        categoryRepository.deleteById(id);
    }
}
