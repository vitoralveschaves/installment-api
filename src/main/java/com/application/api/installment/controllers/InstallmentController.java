package com.application.api.installment.controllers;

import com.application.api.installment.dto.InstallmentResponseDto;
import com.application.api.installment.entities.Installment;
import com.application.api.installment.services.InstallmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/installments")
@RequiredArgsConstructor
public class InstallmentController {

    private final InstallmentService installmentService;

    @GetMapping
    public ResponseEntity<Page<InstallmentResponseDto>> getInstallments(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "page-size", defaultValue = "6") Integer pageSize,
            @RequestParam(value = "month", required = false) String month,
            @RequestParam(value = "year", required = false) String year,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "category", required = false) String category) {
        Page<Installment> installments = installmentService.getInstallments(month, year, page, pageSize, search, category);
        Page<InstallmentResponseDto> installmentResponseDTOPage = installments.map(InstallmentResponseDto::new);
        return ResponseEntity.ok(installmentResponseDTOPage);
    }
}
