package com.application.api.installment.service;

import com.application.api.installment.dto.RoleRequestDto;
import com.application.api.installment.dto.RoleResponseDto;

import java.util.List;

public interface RoleService {
    RoleResponseDto createRole(RoleRequestDto request);
    List<RoleResponseDto> getAllRoles();
    RoleResponseDto getById(Long id);
    void addRoleToUser(Long id, String roleName);
}
