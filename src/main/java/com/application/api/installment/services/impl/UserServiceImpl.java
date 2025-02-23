package com.application.api.installment.services.impl;

import com.application.api.installment.controllers.dto.LoginRequestDto;
import com.application.api.installment.controllers.dto.LoginResponseDto;
import com.application.api.installment.controllers.dto.UserRequestDto;
import com.application.api.installment.controllers.dto.UserResponseDto;
import com.application.api.installment.entities.User;
import com.application.api.installment.repositories.RoleRepository;
import com.application.api.installment.repositories.UserRepository;
import com.application.api.installment.security.TokenService;
import com.application.api.installment.services.RoleService;
import com.application.api.installment.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RoleService roleService;

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
        roleService.addRoleToUser(user.getId(), "BASIC");
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(), user.isActive());
    }

    @Override
    public Page<UserResponseDto> getAllPagination(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.map(UserResponseDto::new);
    }

    @Override
    public List<UserResponseDto> getAll() {
        List<User> usersList = userRepository.findAll();
        return usersList.stream().map(UserResponseDto::new).toList();
    }

    @Override
    public UserResponseDto getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(), user.isActive());
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        boolean isUser = userRepository.existsById(id);
        if(!isUser) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}
