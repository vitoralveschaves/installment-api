package com.application.api.installment.controllers;

import com.application.api.installment.config.SecurityConfig;
import com.application.api.installment.controllers.swagger.UserSwagger;
import com.application.api.installment.dto.UserRequestDto;
import com.application.api.installment.dto.UserResponseDto;
import com.application.api.installment.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController implements UserSwagger {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody UserRequestDto request) {
        UserResponseDto register = userService.register(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(register.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestParam(value = "page", required = false) Integer page,
                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if(page != null || pageSize != null) {
            Page<UserResponseDto> usersPagination = userService.getAllPagination(page, pageSize);
            return ResponseEntity.ok(usersPagination);
        }
        List<UserResponseDto> usersList = userService.getAll();
        return ResponseEntity.ok(usersList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable("id") String id) {
        UserResponseDto user = userService.getById(UUID.fromString(id));
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") String id) {
        userService.deleteById(UUID.fromString(id));
    }
}
