package com.application.api.installment.controllers;

import com.application.api.installment.controllers.dto.ExpenseRequestDTO;
import com.application.api.installment.controllers.dto.ExpenseResponseDTO;
import com.application.api.installment.controllers.dto.ExpenseUpdateDTO;
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
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> createExpense(@RequestBody @Valid ExpenseRequestDTO request) {
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
    public ResponseEntity<List<ExpenseResponseDTO>> getExpenses(@RequestParam(value = "search", required = false) String search) {
        List<Expense> expenses = expenseService.getExpenses(search);
        List<ExpenseResponseDTO> expenseDtoList = expenses.stream().map(ExpenseResponseDTO::new).toList();
        return ResponseEntity.ok(expenseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> getById(@PathVariable("id") String id) {
        Expense expense = expenseService.getById(UUID.fromString(id));
        ExpenseResponseDTO response = new ExpenseResponseDTO(expense);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        expenseService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody @Valid ExpenseUpdateDTO request) {
        Expense expense = expenseService.getById(UUID.fromString(id));
        expense.setTitle(request.title());
        expenseService.update(expense);
        return ResponseEntity.noContent().build();
    }
}
