package com.application.api.installment.service;

import com.application.api.installment.dto.LoginRequestDto;
import com.application.api.installment.dto.LoginResponseDto;
import com.application.api.installment.dto.UserRequestDto;
import com.application.api.installment.dto.UserResponseDto;
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
}
