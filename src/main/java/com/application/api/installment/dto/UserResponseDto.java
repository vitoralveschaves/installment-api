package com.application.api.installment.dto;

import com.application.api.installment.entities.User;

import java.util.UUID;

public record UserResponseDto(UUID id,
                              String name,
                              String email,
                              boolean active) {
    public UserResponseDto(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.isActive());
    }
}
