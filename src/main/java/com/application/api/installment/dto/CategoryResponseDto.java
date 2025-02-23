package com.application.api.installment.dto;

import com.application.api.installment.entities.Category;

import java.util.UUID;

public record CategoryResponseDto(UUID id, String name) {
    public CategoryResponseDto(Category category) {
        this(category.getId(), category.getName());
    }
}
