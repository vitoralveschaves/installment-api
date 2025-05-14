package com.application.api.installment.service.impl;

import com.application.api.installment.converter.CategoryEntityConverter;
import com.application.api.installment.converter.CategoryResponseConverter;
import com.application.api.installment.dto.CategoryRequestDto;
import com.application.api.installment.dto.CategoryResponseDto;
import com.application.api.installment.model.Category;
import com.application.api.installment.exception.NotFoundException;
import com.application.api.installment.repository.CategoryRepository;
import com.application.api.installment.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;
    private final CategoryResponseConverter categoryResponseConverter;
    private final CategoryEntityConverter categoryEntityConverter;

    @Override
    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto dto) {

        if(Objects.isNull(dto)) {
            LOGGER.error("method=CategoryServiceImpl.createCategory message=Category dto cannot be null");
            throw new RuntimeException("Category dto cannot be null");
        }

        LOGGER.info("stage=init method=CategoryServiceImpl.createCategory message=Category created dto={}", dto);

        Category category = categoryEntityConverter.apply(dto);

        categoryRepository.save(category);

        LOGGER.info("stage=end method=CategoryServiceImpl.createCategory categoryId={} categoryName={}",
                category.getId(), category.getName());

        return categoryResponseConverter.apply(category);
    }

    @Override
    public List<CategoryResponseDto> getCategories() {

        LOGGER.info("stage=init method=CategoryServiceImpl.getCategories");
        List<Category> categoryList = categoryRepository.findAll();

        List<CategoryResponseDto> categories = categoryList.stream().map(categoryResponseConverter).toList();
        LOGGER.info("stage=init method=CategoryServiceImpl.getCategories");

        return categories;
    }

    @Override
    public CategoryResponseDto getByUuid(UUID id) {

        LOGGER.info("stage=init method=CategoryServiceImpl.getByUuid categoryId={}", id);

        var category = getById(id);

        if(category.isEmpty()) {
            LOGGER.error("stage=error method=CategoryServiceImpl.getByUuid, message=Category not found");
            throw new NotFoundException("Category not found");
        }

        LOGGER.info("stage=end method=CategoryServiceImpl.getByUuid message=Category fetched categoryId={} categoryName={}",
                category.get().getId(), category.get().getName());

        return categoryResponseConverter.apply(category.get());
    }

    @Override
    public Optional<Category> getById(UUID id) {
        if(id != null) {
            return categoryRepository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteCategory(UUID id) {

        LOGGER.info("stage=init method=CategoryServiceImpl.deleteCategory categoryId={}", id);

        if(!categoryRepository.existsById(id)) {
            LOGGER.error("method=CategoryServiceImpl.deleteCategory message=Category not found");
            throw new NotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);

        LOGGER.info("stage=end method=CategoryServiceImpl.deleteCategory message=Category deleted");
    }
}
