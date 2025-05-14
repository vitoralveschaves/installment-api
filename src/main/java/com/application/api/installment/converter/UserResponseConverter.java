package com.application.api.installment.converter;

import com.application.api.installment.dto.UserResponseDto;
import com.application.api.installment.model.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserResponseConverter implements Function<User, UserResponseDto> {

    @Override
    public UserResponseDto apply(User value) {
        return UserResponseDto.builder()
                .id(value.getId())
                .name(value.getName())
                .email(value.getEmail())
                .active(value.isActive())
                .build();
    }
}
