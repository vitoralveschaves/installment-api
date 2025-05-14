package com.application.api.installment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginResponseDto {

    private String name;

    private String email;

    private List<String> roles;

    private String accessToken;

    private Long expiresAt;
}
