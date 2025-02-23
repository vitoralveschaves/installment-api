package com.application.api.installment.services.impl;

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
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
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
