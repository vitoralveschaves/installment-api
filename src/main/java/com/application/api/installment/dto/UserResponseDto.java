package com.application.api.installment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserResponseDto {

    private String id;

    private String name;

    private String email;

    private boolean active;
}
