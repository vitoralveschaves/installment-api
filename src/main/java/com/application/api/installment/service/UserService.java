package com.application.api.installment.service;

import com.application.api.installment.dto.PaginationResponseDto;
import com.application.api.installment.dto.UserRequestDto;
import com.application.api.installment.dto.UserResponseDto;

import java.util.UUID;

public interface UserService {
    UserResponseDto register(UserRequestDto request);
    PaginationResponseDto<UserResponseDto> getAllPagination(Integer page, Integer pageSize);
    UserResponseDto getById(UUID id);
    UserResponseDto getActiveUserById(UUID id);
    UserResponseDto getActiveUserByEmail(String email);
    void deleteById(UUID id);
    void deleteByEmail(String email);
    void activeByEmail(String email);
}
