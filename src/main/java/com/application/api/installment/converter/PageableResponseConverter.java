package com.application.api.installment.converter;

import com.application.api.installment.dto.PageableResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PageableResponseConverter {

    private static final Integer ONE = 1;

    public PageableResponseDto apply(Page<?> page) {

        if(Objects.isNull(page)) {
            return null;
        }

        return PageableResponseDto.builder()
                .size(page.getSize())
                .number(page.getNumber() + ONE)
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}
