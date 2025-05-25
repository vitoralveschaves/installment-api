package com.application.api.installment.converter;

import com.application.api.installment.dto.PaginationResponseDto;
import com.application.api.installment.dto.PageableResponseDto;
import com.application.api.installment.dto.UserResponseDto;
import com.application.api.installment.model.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPaginationResponseConverter {

    private final UserResponseConverter userResponseConverter;
    private final PageableResponseConverter pageableResponseConverter;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserPaginationResponseConverter.class);

    public PaginationResponseDto<UserResponseDto> apply(Page<User> userPage) {

        LOGGER.info("stage=init method=InstallmentPaginationResponseConverter.apply");

        PageableResponseDto pageable = pageableResponseConverter.apply(userPage);

        var users = userPage.getContent();

        PaginationResponseDto<UserResponseDto> response = PaginationResponseDto.<UserResponseDto>builder()
                .page(pageable)
                .content(users.stream().map(userResponseConverter).toList())
                .build();

        LOGGER.info("stage=end method=InstallmentPaginationResponseConverter.apply");
        return response;
    }
}