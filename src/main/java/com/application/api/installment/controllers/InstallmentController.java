package com.application.api.installment.controllers;

import com.application.api.installment.controllers.dto.InstallmentResponseDTO;
import com.application.api.installment.entities.Installment;
import com.application.api.installment.services.InstallmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "installment")
@RequiredArgsConstructor
public class InstallmentController {

    private final InstallmentService installmentService;

    @GetMapping
    public ResponseEntity<List<InstallmentResponseDTO>> getInstallments(
            @RequestParam(value = "month", defaultValue = "01") String month,
            @RequestParam(value = "year", defaultValue = "2025") String year) {
        List<Installment> installments = installmentService.getInstallments(month, year);
        List<InstallmentResponseDTO> installmentsDto = installments
                .stream().map(InstallmentResponseDTO::new).toList();
        return ResponseEntity.ok(installmentsDto);
    }
}
