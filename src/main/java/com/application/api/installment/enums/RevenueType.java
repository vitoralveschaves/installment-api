package com.application.api.installment.enums;

import lombok.Getter;

@Getter
public enum RevenueType {

    INCOME("Income"),
    INVESTMENT("Investment");

    private final String description;

    RevenueType(String description) {
        this.description = description;
    }
}
