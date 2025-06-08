package com.application.api.installment.controller;

import com.application.api.installment.configuration.SecurityConfiguration;
import com.application.api.installment.controller.swagger.RoleSwagger;
import com.application.api.installment.dto.RoleRequestDto;
import com.application.api.installment.dto.RoleResponseDto;
import com.application.api.installment.service.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
@RequiredArgsConstructor
public class RoleController implements RoleSwagger {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getAllRoles(@RequestHeader(value = "Accept-Language", required = false) String language) {
        List<RoleResponseDto> rolesList = roleService.getAllRoles();
        return ResponseEntity.ok(rolesList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> getById(@RequestHeader(value = "Accept-Language", required = false) String language,
                                                   @PathVariable("id") Long id) {
        var response = roleService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/user")
    public ResponseEntity<Void> addRoleToUser(@RequestHeader(value = "Accept-Language", required = false) String language,
                                              @PathVariable("id") Long id, @RequestBody @Valid RoleRequestDto request) {
        roleService.addRoleToUser(id, request.getName());
        return ResponseEntity.noContent().build();
    }
}
