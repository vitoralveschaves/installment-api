package com.application.api.installment.controllers;

import com.application.api.installment.controllers.dto.RoleRequestDTO;
import com.application.api.installment.controllers.dto.RoleResponseDTO;
import com.application.api.installment.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Void> createRole(@RequestBody RoleRequestDTO request) {
        RoleResponseDTO role = roleService.createRole(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        List<RoleResponseDTO> rolesList = roleService.getAllRoles();
        return ResponseEntity.ok(rolesList);
    }

    @PutMapping("/users/{id}/roles")
    @ResponseStatus(HttpStatus.OK)
    public void addRoleToUser(@PathVariable("id") String id, @RequestBody RoleRequestDTO request) {
        roleService.addRoleToUser(UUID.fromString(id), request.name());
    }
}
