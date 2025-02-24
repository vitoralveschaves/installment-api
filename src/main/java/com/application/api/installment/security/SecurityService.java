package com.application.api.installment.security;

import com.application.api.installment.entities.User;
import com.application.api.installment.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    public User getAuthenticationUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof User) {
            return (User) principal;
        }
        throw new NotFoundException("Usuário não encontrado");
    }
}
