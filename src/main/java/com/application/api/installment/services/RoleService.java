package com.application.api.installment.services;

import com.application.api.installment.dto.RoleRequestDto;
import com.application.api.installment.dto.RoleResponseDto;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    RoleResponseDto createRole(RoleRequestDto request);
    List<RoleResponseDto> getAllRoles();
    void addRoleToUser(UUID id, String roleName);
}
