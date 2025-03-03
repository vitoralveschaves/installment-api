package com.application.api.installment.dto;

import java.util.List;

public record LoginResponseDto(String name,
                               String email,
                               List<String> roles,
                               String accessToken,
                               Long expiresAt) {
}
