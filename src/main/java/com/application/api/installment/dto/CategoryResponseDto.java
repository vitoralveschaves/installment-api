package com.application.api.installment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CategoryResponseDto {

    private String id;

    private String name;
}
