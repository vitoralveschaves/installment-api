package com.application.api.installment.controllers;

import com.application.api.installment.config.SecurityConfig;
import com.application.api.installment.controllers.swagger.RoleSwagger;
import com.application.api.installment.dto.RoleRequestDto;
import com.application.api.installment.dto.RoleResponseDto;
import com.application.api.installment.services.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoleController implements RoleSwagger {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Void> createRole(@RequestBody RoleRequestDto request) {
        RoleResponseDto role = roleService.createRole(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getAllRoles() {
        List<RoleResponseDto> rolesList = roleService.getAllRoles();
        return ResponseEntity.ok(rolesList);
    }

    @PutMapping("/{id}/users")
    @ResponseStatus(HttpStatus.OK)
    public void addRoleToUser(@PathVariable("id") String id, @RequestBody RoleRequestDto request) {
        roleService.addRoleToUser(UUID.fromString(id), request.name());
    }
}
