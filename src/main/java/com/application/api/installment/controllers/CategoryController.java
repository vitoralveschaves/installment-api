package com.application.api.installment.controllers;

import com.application.api.installment.dto.CategoryRequestDto;
import com.application.api.installment.dto.CategoryResponseDto;
import com.application.api.installment.entities.Category;
import com.application.api.installment.exceptions.NotFoundException;
import com.application.api.installment.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody @Valid CategoryRequestDto request) {
        Category category = request.toEntity();
        categoryService.createCategory(category);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getCategories() {
        List<Category> categories = categoryService.getCategories();
        List<CategoryResponseDto> categoryResponse = categories
                .stream().map(CategoryResponseDto::new).toList();
        return ResponseEntity.ok(categoryResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable("id") String id) {
        Category category = categoryService.getById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
        return ResponseEntity.ok(new CategoryResponseDto(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") String id) {
        categoryService.deleteCategory(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }
}