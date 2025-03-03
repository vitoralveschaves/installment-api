package com.application.api.installment.services.impl;

import com.application.api.installment.dto.LoginRequestDto;
import com.application.api.installment.dto.LoginResponseDto;
import com.application.api.installment.dto.UserRequestDto;
import com.application.api.installment.dto.UserResponseDto;
import com.application.api.installment.entities.User;
import com.application.api.installment.exceptions.AlreadyExistsException;
import com.application.api.installment.exceptions.NotFoundException;
import com.application.api.installment.repositories.UserRepository;
import com.application.api.installment.security.TokenService;
import com.application.api.installment.services.RoleService;
import com.application.api.installment.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
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
    private final RoleService roleService;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        try {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(request.email(), request.password());
            Authentication authenticate = authenticationManager.authenticate(auth);
            User user = (User) authenticate.getPrincipal();
            String token = tokenService.generateToken(user);
            List<String> roles = user.getUserRoles()
                    .stream()
                    .map(role -> role.getRole().getName())
                    .toList();
            return new LoginResponseDto(user.getName(), user.getEmail(), roles, token);
        } catch (InternalAuthenticationServiceException e) {
            throw new BadCredentialsException("Usuário inexistente ou senha inválida");
        }
    }

    @Override
    @Transactional
    public UserResponseDto register(UserRequestDto request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new AlreadyExistsException("Usuário já existe");
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
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(), user.isActive());
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        boolean isUser = userRepository.existsById(id);
        if(!isUser) {
            throw new NotFoundException("Usuário não encontrado");
        }
        userRepository.deleteById(id);
    }
}
