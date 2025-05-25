package com.application.api.installment.controller;

import com.application.api.installment.controller.swagger.InstallmentSwagger;
import com.application.api.installment.dto.InstallmentBalanceResponseDto;
import com.application.api.installment.dto.InstallmentResponseDto;
import com.application.api.installment.dto.PaginationResponseDto;
import com.application.api.installment.service.InstallmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/installments")
@RequiredArgsConstructor
public class InstallmentController implements InstallmentSwagger {

    private final InstallmentService installmentService;

    @GetMapping
    public ResponseEntity<PaginationResponseDto<InstallmentResponseDto>> getInstallments(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "quantity", defaultValue = "6") Integer pageSize,
            @RequestParam(value = "month", required = false) String month,
            @RequestParam(value = "year", required = false) String year,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "category", required = false) String category) {
        var installments = installmentService.getInstallments(month, year, page, pageSize, search, category);
        return ResponseEntity.ok(installments);
    }

    @GetMapping("/balance")
    public ResponseEntity<InstallmentBalanceResponseDto> getExpenseBalance(
            @RequestParam(value = "month", required = false) String month) {
        var response = installmentService.getInstallmentBalance(month);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/pay/{installmentId}")
    public ResponseEntity<Void> pay(@PathVariable("installmentId") String installmentId) {
        installmentService.pay(UUID.fromString(installmentId));
        return ResponseEntity.noContent().build();
    }
}
