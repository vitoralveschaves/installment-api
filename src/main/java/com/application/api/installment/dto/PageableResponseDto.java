package com.application.api.installment.dto;

import lombok.*;

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
