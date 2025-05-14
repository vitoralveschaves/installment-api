package com.application.api.installment.controller;

import com.application.api.installment.configuration.SecurityConfiguration;
import com.application.api.installment.controller.swagger.RoleSwagger;
import com.application.api.installment.dto.RoleRequestDto;
import com.application.api.installment.dto.RoleResponseDto;
import com.application.api.installment.service.RoleService;
import com.application.api.installment.util.LocationBuilderUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoleController implements RoleSwagger {

    private final RoleService roleService;
    private final LocationBuilderUtil locationUtils;

    @PostMapping
    public ResponseEntity<Void> createRole(@RequestBody RoleRequestDto request) {

        RoleResponseDto role = roleService.createRole(request);
        URI location = locationUtils.buildLocation(role.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getAllRoles() {
        List<RoleResponseDto> rolesList = roleService.getAllRoles();
        return ResponseEntity.ok(rolesList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> getById(@PathVariable("id") String id) {
        var response = roleService.getById(UUID.fromString(id));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/user")
    @ResponseStatus(HttpStatus.OK)
    public void addRoleToUser(@PathVariable("id") String id, @RequestBody RoleRequestDto request) {
        roleService.addRoleToUser(UUID.fromString(id), request.getName());
    }
}
