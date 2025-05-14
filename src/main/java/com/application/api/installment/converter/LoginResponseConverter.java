package com.application.api.installment.converter;

import com.application.api.installment.dto.LoginResponseDto;
import com.application.api.installment.model.User;
import com.application.api.installment.model.UserRole;
import com.application.api.installment.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class LoginResponseConverter implements Function<User, LoginResponseDto> {

    private final TokenService tokenService;

    @Override
    public LoginResponseDto apply(User value) {

        if(Objects.isNull(value)) {
            throw new RuntimeException("User data cannot be null");
        }

        var token = tokenService.generateToken(value);
        var expiresAt = tokenService.getExpiresAt(token);

        return LoginResponseDto.builder()
                .name(value.getName())
                .email(value.getEmail())
                .roles(getUserRolesName(value.getUserRoles()))
                .accessToken(token)
                .expiresAt(expiresAt)
                .build();
    }

    private List<String> getUserRolesName(List<UserRole> userRoles) {
        return userRoles
                .stream()
                .map(role -> role.getRole().getName())
                .toList();
    }
}
