package com.application.api.installment.services;

import com.application.api.installment.controllers.dto.RoleRequestDTO;
import com.application.api.installment.controllers.dto.RoleResponseDTO;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    RoleResponseDTO createRole(RoleRequestDTO request);
    List<RoleResponseDTO> getAllRoles();
    void addRoleToUser(UUID id, String roleName);
}
