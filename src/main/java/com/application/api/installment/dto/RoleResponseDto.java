package com.application.api.installment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Builder
@ToString
public class RoleResponseDto {
    private UUID id;
    private String name;
}
