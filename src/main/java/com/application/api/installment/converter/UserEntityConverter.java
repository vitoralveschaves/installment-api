package com.application.api.installment.converter;

import com.application.api.installment.dto.UserRequestDto;
import com.application.api.installment.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserEntityConverter implements Function<UserRequestDto, User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User apply(UserRequestDto value) {
        return User.builder()
                .name(value.getName())
                .email(value.getEmail())
                .active(true)
                .password(passwordConverter(value.getPassword()))
                .build();
    }

    private String passwordConverter(String password) {
        if(Objects.isNull(password)) {
            throw new RuntimeException("Password cannot be null");
        }
        return passwordEncoder.encode(password);
    }
}
