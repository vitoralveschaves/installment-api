package com.application.api.installment.controller;

import com.application.api.installment.configuration.SecurityConfiguration;
import com.application.api.installment.controller.swagger.RoleSwagger;
import com.application.api.installment.dto.RoleRequestDto;
import com.application.api.installment.dto.RoleResponseDto;
import com.application.api.installment.service.RoleService;
import com.application.api.installment.util.LocationBuilderUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
@RequiredArgsConstructor
public class RoleController implements RoleSwagger {

    private final RoleService roleService;
    private final LocationBuilderUtil locationUtils;

    @PostMapping
    public ResponseEntity<Void> createRole(@RequestHeader(value = "Accept-Language", required = false) String language,
                                           @RequestBody @Valid RoleRequestDto request) {
        RoleResponseDto role = roleService.createRole(request);
        URI location = locationUtils.buildLocation(role.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getAllRoles(@RequestHeader(value = "Accept-Language", required = false) String language) {
        List<RoleResponseDto> rolesList = roleService.getAllRoles();
        return ResponseEntity.ok(rolesList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> getById(@RequestHeader(value = "Accept-Language", required = false) String language,
                                                   @PathVariable("id") String id) {
        var response = roleService.getById(UUID.fromString(id));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/user")
    public ResponseEntity<Void> addRoleToUser(@RequestHeader(value = "Accept-Language", required = false) String language,
                                              @PathVariable("id") String id, @RequestBody @Valid RoleRequestDto request) {
        roleService.addRoleToUser(UUID.fromString(id), request.getName());
        return ResponseEntity.noContent().build();
    }
}
