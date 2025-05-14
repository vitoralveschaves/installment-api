package com.application.api.installment.controller;

import com.application.api.installment.controller.swagger.AuthSwagger;
import com.application.api.installment.dto.LoginRequestDto;
import com.application.api.installment.dto.LoginResponseDto;
import com.application.api.installment.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController implements AuthSwagger {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request) {
        LoginResponseDto login = userService.login(request);
        return ResponseEntity.ok(login);
    }
}
