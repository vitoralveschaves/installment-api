package com.application.api.installment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserResponseDto {

    private UUID id;

    private String name;

    private String email;

    private boolean active;
}
