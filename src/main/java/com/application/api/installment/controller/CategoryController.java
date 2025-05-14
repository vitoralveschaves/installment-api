package com.application.api.installment.controller;

import com.application.api.installment.controller.swagger.CategorySwagger;
import com.application.api.installment.dto.CategoryRequestDto;
import com.application.api.installment.dto.CategoryResponseDto;
import com.application.api.installment.service.CategoryService;
import com.application.api.installment.util.LocationBuilderUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController implements CategorySwagger {

    private final CategoryService categoryService;
    private final LocationBuilderUtil locationUtils;

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody @Valid CategoryRequestDto request) {

        CategoryResponseDto category = categoryService.createCategory(request);

        var location = locationUtils.buildLocation(category.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getCategories() {
        List<CategoryResponseDto> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable("id") String id) {
        CategoryResponseDto response = categoryService.getByUuid(UUID.fromString(id));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") String id) {
        categoryService.deleteCategory(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }
}