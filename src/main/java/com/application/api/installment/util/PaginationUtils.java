package com.application.api.installment.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtils {

    public boolean isAbleToPagination(Integer page, Integer pageSize) {
        return (page != null && page > 0) && pageSize > 0;
    }

    public Pageable getPageable(Integer page, Integer pageSize) {
        page = page == 0 ? 1 : page;
        pageSize = pageSize == 0 ? 1 : pageSize;

        return PageRequest.of(page - 1, pageSize);
    }
}
