package com.application.api.installment.controllers;

import com.application.api.installment.dto.ExpenseRequestDto;
import com.application.api.installment.dto.ExpenseResponseDto;
import com.application.api.installment.dto.ExpenseUpdateDto;
import com.application.api.installment.entities.Category;
import com.application.api.installment.entities.Expense;
import com.application.api.installment.services.CategoryService;
import com.application.api.installment.services.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> createExpense(@RequestBody @Valid ExpenseRequestDto request) {
        Optional<Category> category = categoryService.getById(request.categoryId());
        Expense expense = request.toEntity();
        category.ifPresent(expense::setCategory);
        Expense expenseResponse = expenseService.createExpense(expense);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(expenseResponse.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> getExpenses(@RequestParam(value = "search", required = false) String search) {
        List<Expense> expenses = expenseService.getExpenses(search);
        List<ExpenseResponseDto> expenseDtoList = expenses.stream().map(ExpenseResponseDto::new).toList();
        return ResponseEntity.ok(expenseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> getById(@PathVariable("id") String id) {
        Expense expense = expenseService.getById(UUID.fromString(id));
        ExpenseResponseDto response = new ExpenseResponseDto(expense);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        expenseService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody @Valid ExpenseUpdateDto request) {
        Expense expense = expenseService.getById(UUID.fromString(id));
        expense.setTitle(request.title());
        expenseService.update(expense);
        return ResponseEntity.noContent().build();
    }
}
