package com.application.api.installment.controller;

import com.application.api.installment.controller.swagger.CategorySwagger;
import com.application.api.installment.dto.CategoryRequestDto;
import com.application.api.installment.dto.CategoryResponseDto;
import com.application.api.installment.service.CategoryService;
import com.application.api.installment.util.LocationBuilderUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController implements CategorySwagger {

    private final CategoryService categoryService;
    private final LocationBuilderUtils locationUtils;

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestHeader(value = "Accept-Language", required = false) String language,
                                               @RequestBody @Valid CategoryRequestDto request) {

        CategoryResponseDto category = categoryService.createCategory(request);
        var location = locationUtils.buildLocation(category.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getCategories(@RequestHeader(value = "Accept-Language", required = false) String language) {
        List<CategoryResponseDto> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getById(@RequestHeader(value = "Accept-Language", required = false) String language,
                                                       @PathVariable("id") String id) {
        CategoryResponseDto response = categoryService.getByUuid(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@RequestHeader(value = "Accept-Language", required = false) String language,
                                               @PathVariable("id") String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}