package com.application.api.installment.security;

import com.application.api.installment.exceptions.NotFoundException;
import com.application.api.installment.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmailWithRoles(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }
}
