package com.application.api.installment.security;

import com.application.api.installment.dto.UserAuthenticationData;
import com.application.api.installment.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    public UserAuthenticationData getAuthenticationUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserAuthenticationData) {
            return (UserAuthenticationData) principal;
        }
        throw new NotFoundException("User not found");
    }
}
