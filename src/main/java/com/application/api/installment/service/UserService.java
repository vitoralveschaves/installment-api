package com.application.api.installment.service;

import com.application.api.installment.dto.PaginationResponseDto;
import com.application.api.installment.dto.UserRequestDto;
import com.application.api.installment.dto.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRequestDto request);
    PaginationResponseDto<UserResponseDto> getAllPagination(Integer page, Integer pageSize);
    UserResponseDto getById(String id);
    UserResponseDto getActiveUserById(String id);
    UserResponseDto getActiveUserByEmail(String email);
    void deleteById(String id);
    void deleteByEmail(String email);
    void activeByEmail(String email);
}
