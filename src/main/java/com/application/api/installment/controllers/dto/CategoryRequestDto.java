package com.application.api.installment.controllers.dto;

import com.application.api.installment.entities.Category;
import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDto(@NotBlank(message = "Campo obrigatório") String name) {
    public Category toEntity() {
        Category category = new Category();
        category.setName(this.name);
        return category;
    }
}
