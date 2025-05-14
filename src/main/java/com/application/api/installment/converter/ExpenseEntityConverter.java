package com.application.api.installment.converter;

import com.application.api.installment.dto.ExpenseRequestDto;
import com.application.api.installment.model.Category;
import com.application.api.installment.model.Expense;
import com.application.api.installment.model.User;
import com.application.api.installment.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class ExpenseEntityConverter implements BiFunction<ExpenseRequestDto, User, Expense> {

    private final CategoryService categoryService;

    @Override
    public Expense apply(ExpenseRequestDto dto, User user) {
        return Expense.builder()
                .title(dto.getTitle())
                .totalValue(dto.getTotalValue())
                .quantityInstallments(dto.getQuantityInstallments())
                .initialDate(dto.getInitialDate())
                .user(user)
                .category(buildCategory(dto.getCategoryId()))
                .build();
    }

    private Category buildCategory(UUID categoryId) {
        Optional<Category> category = categoryService.getById(categoryId);
        return category.orElse(null);
    }
}
