package com.application.api.installment.controllers;

import com.application.api.installment.controllers.dto.RevenueRequestDTO;
import com.application.api.installment.controllers.mappers.RevenueMapper;
import com.application.api.installment.entities.Revenue;
import com.application.api.installment.services.RevenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/revenue")
@RequiredArgsConstructor
public class RevenueController {
    private final RevenueService revenueService;
    private final RevenueMapper revenueMapper;

    @PostMapping
    public ResponseEntity<Object> createRevenue(@RequestBody @Valid RevenueRequestDTO request) {
        Revenue revenue = revenueMapper.dtoToEntity(request);
        Revenue revenueResponse = revenueService.createRevenue(revenue);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(revenueResponse.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
