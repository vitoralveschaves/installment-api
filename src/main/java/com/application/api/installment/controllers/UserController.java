package com.application.api.installment.controllers;

import com.application.api.installment.controllers.dto.*;
import com.application.api.installment.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request) {
        LoginResponseDto login = userService.login(request);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto request) {
        UserResponseDto register = userService.register(request);
        return ResponseEntity.ok(register);
    }

    @PostMapping("/register-role")
    public ResponseEntity<RoleResponseDTO> registerRole(@RequestBody RoleRequestDTO request) {
        RoleResponseDTO role = userService.registerRole(request);
        return ResponseEntity.ok(role);
    }
}
