package com.application.api.installment.service;

import com.application.api.installment.dto.ChangePasswordRequestDto;
import com.application.api.installment.dto.LoginRequestDto;
import com.application.api.installment.dto.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto dto);
    void changePassword(ChangePasswordRequestDto dto);
}
