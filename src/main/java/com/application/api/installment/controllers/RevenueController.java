package com.application.api.installment.controllers;

import com.application.api.installment.controllers.dto.RevenueRequestDTO;
import com.application.api.installment.controllers.dto.RevenueResponseDTO;
import com.application.api.installment.controllers.dto.RevenueUpdateDTO;
import com.application.api.installment.controllers.mappers.RevenueMapper;
import com.application.api.installment.entities.Revenue;
import com.application.api.installment.services.RevenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
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

    @GetMapping
    public ResponseEntity<List<RevenueResponseDTO>> getRevenues() {
        List<Revenue> revenues = revenueService.getRevenues();
        List<RevenueResponseDTO> revenueDtoList = revenues.stream().map(revenueMapper::entityToDto).toList();
        return ResponseEntity.ok(revenueDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RevenueResponseDTO> getById(@PathVariable("id") String id) {
        Revenue revenue = revenueService.getById(UUID.fromString(id));
        RevenueResponseDTO response = revenueMapper.entityToDto(revenue);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        revenueService.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody @Valid RevenueUpdateDTO request) {
        Revenue revenue = revenueService.getById(UUID.fromString(id));
        revenue.setTitle(request.title());
        revenueService.update(revenue);
        return ResponseEntity.noContent().build();
    }
}
