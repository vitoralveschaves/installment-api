package com.application.api.installment.controllers;

import com.application.api.installment.controllers.dto.LoginRequestDto;
import com.application.api.installment.controllers.dto.LoginResponseDto;
import com.application.api.installment.controllers.dto.UserRequestDto;
import com.application.api.installment.controllers.dto.UserResponseDto;
import com.application.api.installment.services.UserService;
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
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto login = userService.login(request);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto request) {
        UserResponseDto register = userService.register(request);
        return ResponseEntity.ok(register);
    }
}
