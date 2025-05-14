package com.application.api.installment.service;

import com.application.api.installment.dto.RoleRequestDto;
import com.application.api.installment.dto.RoleResponseDto;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    RoleResponseDto createRole(RoleRequestDto request);
    List<RoleResponseDto> getAllRoles();
    RoleResponseDto getById(UUID id);
    void addRoleToUser(UUID id, String roleName);
}
