package com.application.api.installment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponseDto {
    private Integer size;
    private Integer number;
    private Long totalElements;
    private Integer totalPages;
    private Boolean first;
    private Boolean last;
}
