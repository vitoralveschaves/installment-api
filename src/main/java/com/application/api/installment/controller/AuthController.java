package com.application.api.installment.controller;

import com.application.api.installment.controller.swagger.AuthSwagger;
import com.application.api.installment.dto.ChangePasswordRequestDto;
import com.application.api.installment.dto.LoginRequestDto;
import com.application.api.installment.dto.LoginResponseDto;
import com.application.api.installment.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthSwagger {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestHeader(value = "Accept-Language", required = false) String language,
                                                  @RequestBody @Valid LoginRequestDto request) {
        LoginResponseDto login = authService.login(request);
        return ResponseEntity.ok(login);
    }

    @PatchMapping("/change")
    public ResponseEntity<Void> changePassword(@RequestHeader(value = "Accept-Language", required = false) String language,
                                               @RequestBody @Valid ChangePasswordRequestDto request) {
        authService.changePassword(request);
        return ResponseEntity.noContent().build();
    }
}
