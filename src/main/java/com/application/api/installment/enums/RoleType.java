package com.application.api.installment.enums;

import lombok.Getter;

@Getter
public enum RoleType {

    ADMIN("Admin"),
    USER("User");

    private final String role;

    RoleType(String role) {
        this.role = role;
    }
}
