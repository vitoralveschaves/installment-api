package com.application.api.installment.service;

import com.application.api.installment.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDto register(UserRequestDto request);
    Page<UserResponseDto> getAllPagination(Integer page, Integer pageSize);
    List<UserResponseDto> getAll();
    UserResponseDto getById(UUID id);
    UserResponseDto getActiveUserById(UUID id);
    UserResponseDto getActiveUserByEmail(String email);
    void deleteById(UUID id);
    void deleteByEmail(String email);
    void activeByEmail(String email);
}
