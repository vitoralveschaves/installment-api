package com.application.api.installment.controller;

import com.application.api.installment.configuration.SecurityConfiguration;
import com.application.api.installment.controller.swagger.UserSwagger;
import com.application.api.installment.dto.UserRequestDto;
import com.application.api.installment.dto.UserResponseDto;
import com.application.api.installment.service.UserService;
import com.application.api.installment.util.LocationBuilderUtil;
import com.application.api.installment.util.PaginationUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController implements UserSwagger {

    private final UserService userService;
    private final LocationBuilderUtil locationUtils;
    private final PaginationUtils paginationUtils;

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody @Valid UserRequestDto request) {
        UserResponseDto register = userService.register(request);
        URI location = locationUtils.buildLocation(register.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestParam(value = "page", required = false) Integer page,
                                       @RequestParam(value = "quantity", defaultValue = "6") Integer pageSize) {
        if(paginationUtils.isAbleToPagination(page, pageSize)) {
            Page<UserResponseDto> usersPagination = userService.getAllPagination(page, pageSize);
            return ResponseEntity.ok(usersPagination);
        }
        List<UserResponseDto> usersList = userService.getAll();
        return ResponseEntity.ok(usersList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable("id") String id) {
        UserResponseDto user = userService.getActiveUserById(UUID.fromString(id));
        return ResponseEntity.ok(user);
    }

    @GetMapping(params = "email")
    public ResponseEntity<UserResponseDto> getByEmail(@RequestParam(value = "email") String email) {
        UserResponseDto user = userService.getActiveUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") String id) {
        userService.deleteById(UUID.fromString(id));
    }

    @PatchMapping(params = "email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByEmail(@RequestParam(value = "email") String email) {
        userService.deleteByEmail(email);
    }
}
