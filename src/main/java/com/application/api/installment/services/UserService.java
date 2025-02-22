package com.application.api.installment.services;

import com.application.api.installment.controllers.dto.LoginRequestDto;
import com.application.api.installment.controllers.dto.LoginResponseDto;
import com.application.api.installment.controllers.dto.RoleRequestDTO;
import com.application.api.installment.controllers.dto.RoleResponseDTO;
import com.application.api.installment.controllers.dto.UserRequestDto;
import com.application.api.installment.controllers.dto.UserResponseDto;

public interface UserService {
    LoginResponseDto login(LoginRequestDto request);
    UserResponseDto register(UserRequestDto request);
    RoleResponseDTO registerRole(RoleRequestDTO request);
}
