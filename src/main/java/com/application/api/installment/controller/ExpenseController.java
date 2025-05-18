package com.application.api.installment.controller;

import com.application.api.installment.configuration.SecurityConfiguration;
import com.application.api.installment.controller.swagger.ExpenseSwagger;
import com.application.api.installment.dto.ExpenseRequestDto;
import com.application.api.installment.dto.ExpenseResponseDto;
import com.application.api.installment.dto.ExpenseUpdateDto;
import com.application.api.installment.service.ExpenseService;
import com.application.api.installment.util.LocationBuilderUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/expenses")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExpenseController implements ExpenseSwagger {

    private final ExpenseService expenseService;
    private final LocationBuilderUtil locationBuilderUtil;

    @PostMapping
    public ResponseEntity<Void> createExpense(@RequestBody @Valid ExpenseRequestDto request) {
        ExpenseResponseDto expenseResponse = expenseService.createExpense(request);
        URI location = locationBuilderUtil.buildLocation(expenseResponse.getExpenseId());
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
