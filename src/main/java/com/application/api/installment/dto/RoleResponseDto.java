package com.application.api.installment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RoleResponseDto {
    private Long id;
    private String name;
}
