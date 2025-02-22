package com.application.api.installment.services.impl;

import com.application.api.installment.controllers.dto.*;
import com.application.api.installment.entities.Role;
import com.application.api.installment.entities.User;
import com.application.api.installment.entities.UserRole;
import com.application.api.installment.repositories.RoleRepository;
import com.application.api.installment.repositories.UserRepository;
import com.application.api.installment.repositories.UserRoleRepository;
import com.application.api.installment.security.TokenService;
import com.application.api.installment.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authenticate = authenticationManager.authenticate(auth);
        User user = (User) authenticate.getPrincipal();
        String token = tokenService.generateToken(user);
        return new LoginResponseDto(token);
    }

    @Override
    @Transactional
    public UserResponseDto register(UserRequestDto request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("User already exists");
        }
        String password = passwordEncoder.encode(request.password());
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(password);
        user.setActive(true);
        userRepository.save(user);
        addRoleToUser(user.getEmail(), "BASIC");
        return new UserResponseDto(user.getName(), user.getEmail(), user.isActive());
    }

    @Override
    @Transactional
    public RoleResponseDTO registerRole(RoleRequestDTO request) {
        if(roleRepository.existsByName(request.name().toUpperCase())) {
            throw new RuntimeException("Role already exists");
        }
        Role role = new Role();
        role.setName(request.name().toUpperCase());
        roleRepository.save(role);
        return new RoleResponseDTO(role.getId(), role.getName());
    }

    private void addRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findByName(roleName.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRoleRepository.save(userRole);
    }
}
