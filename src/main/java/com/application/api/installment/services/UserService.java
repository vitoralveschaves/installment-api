package com.application.api.installment.services;

import com.application.api.installment.controllers.dto.LoginRequestDto;
import com.application.api.installment.controllers.dto.LoginResponseDto;
import com.application.api.installment.controllers.dto.RoleRequestDTO;
import com.application.api.installment.controllers.dto.RoleResponseDTO;
import com.application.api.installment.controllers.dto.UserRequestDto;
import com.application.api.installment.controllers.dto.UserResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface UserService {
    LoginResponseDto login(LoginRequestDto request);
    UserResponseDto register(UserRequestDto request);
    Page<UserResponseDto> getAllPagination(Integer page, Integer pageSize);
    List<UserResponseDto> getAll();
    UserResponseDto getById(UUID id);
    void deleteById(UUID id);
    RoleResponseDTO registerRole(RoleRequestDTO request);
}
