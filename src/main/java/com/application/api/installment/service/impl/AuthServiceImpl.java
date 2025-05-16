package com.application.api.installment.service.impl;

import com.application.api.installment.converter.LoginResponseConverter;
import com.application.api.installment.dto.ChangePasswordRequestDto;
import com.application.api.installment.dto.LoginRequestDto;
import com.application.api.installment.dto.LoginResponseDto;
import com.application.api.installment.exception.NotFoundException;
import com.application.api.installment.model.User;
import com.application.api.installment.repository.UserRepository;
import com.application.api.installment.security.SecurityService;
import com.application.api.installment.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final LoginResponseConverter loginResponseConverter;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
        try {
            LOGGER.info("stage=init method=AuthServiceImpl.login email={}", dto.getEmail());
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    dto.getEmail(), dto.getPassword());

            Authentication authenticate = authenticationManager.authenticate(auth);

            User user = (User) authenticate.getPrincipal();
            var response = loginResponseConverter.apply(user);

            LOGGER.info("stage=end method=AuthServiceImpl.login message=User authenticated userName={}", response.getName());
            return response;

        } catch (InternalAuthenticationServiceException e) {
            LOGGER.error("stage=error method=AuthServiceImpl.login message=Invalid email or password");
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequestDto dto) {

        LOGGER.info("stage=init method=AuthServiceImpl.changePassword");

        User authenticationUser = securityService.getAuthenticationUser();

        String email = authenticationUser.getEmail();
        var optionalUser = userRepository.findByEmail(email);

        User user = optionalUser.orElseGet(() -> {
            LOGGER.error("stage=error method=AuthServiceImpl.changePassword message=User not found");
            throw new NotFoundException("User not found");
        });

        isMatchPassword(dto, user);

        String newPasswordEncoded = passwordEncoder.encode(dto.getNewPassword());
        user.setPassword(newPasswordEncoded);

        userRepository.save(user);
        LOGGER.info("stage=end method=AuthServiceImpl.changePassword message=Password changed");
    }

    private void isMatchPassword(ChangePasswordRequestDto dto, User user) {

        String passwordFromDatabase = user.getPassword();
        var isMatch = passwordEncoder.matches(dto.getOldPassword(), passwordFromDatabase);

        if(!isMatch) {
            throw new BadCredentialsException("Invalid credentials");
        }
    }
}
