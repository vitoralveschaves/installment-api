package com.application.api.installment.converter;

import com.application.api.installment.dto.InstallmentResponseDto;
import com.application.api.installment.dto.PaginationResponseDto;
import com.application.api.installment.dto.PageableResponseDto;
import com.application.api.installment.model.Installment;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstallmentPaginationResponseConverter {

    private final InstallmentResponseConverter installmentResponseConverter;
    private final PageableResponseConverter pageableResponseConverter;
    private static final Logger LOGGER = LoggerFactory.getLogger(InstallmentPaginationResponseConverter.class);

    public PaginationResponseDto<InstallmentResponseDto> apply(Page<Installment> installmentPage) {

        LOGGER.info("stage=init method=InstallmentPaginationResponseConverter.apply");

        PageableResponseDto pageable = pageableResponseConverter.apply(installmentPage);

        var installments = installmentPage.getContent();

        PaginationResponseDto<InstallmentResponseDto> response = PaginationResponseDto.<InstallmentResponseDto>builder()
                .page(pageable)
                .content(installments.stream().map(installmentResponseConverter).toList())
                .build();

        LOGGER.info("stage=end method=InstallmentPaginationResponseConverter.apply");
        return response;
    }
}
