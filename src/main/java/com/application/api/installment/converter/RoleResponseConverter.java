package com.application.api.installment.converter;

import com.application.api.installment.dto.RoleResponseDto;
import com.application.api.installment.model.Role;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RoleResponseConverter implements Function<Role, RoleResponseDto> {

    @Override
    public RoleResponseDto apply(Role value) {
        return RoleResponseDto.builder()
                .id(value.getId())
                .name(value.getName())
                .build();
    }
}
