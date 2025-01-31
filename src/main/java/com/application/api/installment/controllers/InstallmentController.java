package com.application.api.installment.controllers;

import com.application.api.installment.controllers.dto.InstallmentResponseDTO;
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
@RequestMapping(value = "installment")
@RequiredArgsConstructor
public class InstallmentController {

    private final InstallmentService installmentService;

    @GetMapping
    public ResponseEntity<Page<InstallmentResponseDTO>> getInstallments(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "page-size", defaultValue = "6") Integer pageSize,
            @RequestParam(value = "month", defaultValue = "01") String month,
            @RequestParam(value = "year", defaultValue = "2025") String year) {
        Page<Installment> installments = installmentService.getInstallments(month, year, page, pageSize);
        Page<InstallmentResponseDTO> installmentResponseDTOPage = installments.map(InstallmentResponseDTO::new);
        return ResponseEntity.ok(installmentResponseDTOPage);
    }
}
