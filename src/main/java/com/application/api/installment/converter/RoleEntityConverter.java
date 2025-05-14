package com.application.api.installment.converter;

import com.application.api.installment.dto.RoleRequestDto;
import com.application.api.installment.model.Role;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RoleEntityConverter implements Function<RoleRequestDto, Role> {

    @Override
    public Role apply(RoleRequestDto value) {
        return Role.builder()
                .name(value.getName())
                .build();
    }
}
