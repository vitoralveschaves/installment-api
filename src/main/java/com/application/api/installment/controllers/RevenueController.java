package com.application.api.installment.controllers;

import com.application.api.installment.controllers.dto.RevenueRequestDTO;
import com.application.api.installment.controllers.dto.RevenueResponseDTO;
import com.application.api.installment.controllers.mappers.RevenueMapper;
import com.application.api.installment.entities.Revenue;
import com.application.api.installment.services.RevenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/revenue")
@RequiredArgsConstructor
public class RevenueController {
    private final RevenueService revenueService;
    private final RevenueMapper revenueMapper;

    @PostMapping
    public ResponseEntity<Void> createRevenue(@RequestBody @Valid RevenueRequestDTO request) {
        Revenue revenue = revenueMapper.dtoToEntity(request);
        Revenue revenueResponse = revenueService.createRevenue(revenue);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(revenueResponse.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RevenueResponseDTO> getById(@PathVariable("id") String id) {
        Revenue revenue = revenueService.getById(UUID.fromString(id));
        RevenueResponseDTO response = revenueMapper.entityToDto(revenue);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<RevenueResponseDTO>> getAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "page-size", defaultValue = "10", required = false) Integer pageSize) {
        Page<Revenue> pageRevenue = revenueService.getAll(page, pageSize);
        var pageList = pageRevenue.map(revenueMapper::entityToDto);
        return ResponseEntity.ok(pageList);
    }
}
