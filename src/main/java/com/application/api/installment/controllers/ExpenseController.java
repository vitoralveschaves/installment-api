package com.application.api.installment.controllers;

import com.application.api.installment.config.SecurityConfig;
import com.application.api.installment.dto.ExpenseRequestDto;
import com.application.api.installment.dto.ExpenseResponseDto;
import com.application.api.installment.dto.ExpenseUpdateDto;
import com.application.api.installment.entities.Expense;
import com.application.api.installment.services.CategoryService;
import com.application.api.installment.services.ExpenseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/expenses")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> createExpense(@RequestBody @Valid ExpenseRequestDto request) {
        Expense expenseResponse = expenseService.createExpense(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(expenseResponse.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> getExpenses(
            @RequestParam(value = "search", required = false) String search) {
        List<ExpenseResponseDto> expenses = expenseService.getExpenses(search);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> getById(@PathVariable("id") String id) {
        ExpenseResponseDto expense = expenseService.getById(UUID.fromString(id));
        return ResponseEntity.ok(expense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        expenseService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") String id, @RequestBody @Valid ExpenseUpdateDto request) {
        expenseService.update(UUID.fromString(id), request);
    }
}
