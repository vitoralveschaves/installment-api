package com.application.api.installment.converter;

import com.application.api.installment.dto.CategoryRequestDto;
import com.application.api.installment.model.Category;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CategoryEntityConverter implements Function<CategoryRequestDto, Category> {

    @Override
    public Category apply(CategoryRequestDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }
}
