package com.application.api.installment.converter;

import com.application.api.installment.dto.CategoryResponseDto;
import com.application.api.installment.model.Category;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CategoryResponseConverter implements Function<Category, CategoryResponseDto> {

    @Override
    public CategoryResponseDto apply(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
