package com.application.api.installment.service.impl;

import com.application.api.installment.converter.LoginResponseConverter;
import com.application.api.installment.converter.UserEntityConverter;
import com.application.api.installment.converter.UserResponseConverter;
import com.application.api.installment.dto.LoginRequestDto;
import com.application.api.installment.dto.LoginResponseDto;
import com.application.api.installment.dto.UserRequestDto;
import com.application.api.installment.dto.UserResponseDto;
import com.application.api.installment.model.User;
import com.application.api.installment.exception.AlreadyExistsException;
import com.application.api.installment.exception.NotFoundException;
import com.application.api.installment.repository.UserRepository;
import com.application.api.installment.service.RoleService;
import com.application.api.installment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserEntityConverter userEntityConverter;
    private final UserResponseConverter userResponseConverter;
    private final LoginResponseConverter loginResponseConverter;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        try {
            LOGGER.info("stage=init method=UserServiceImpl.login email={}", request.getEmail());
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    request.getEmail(), request.getPassword());

            Authentication authenticate = authenticationManager.authenticate(auth);

            User user = (User) authenticate.getPrincipal();
            var response = loginResponseConverter.apply(user);

            LOGGER.info("stage=end method=UserServiceImpl.login message=User authenticated userName={}", response.getName());
            return response;

        } catch (InternalAuthenticationServiceException e) {
            LOGGER.error("stage=error method=UserServiceImpl.login message=Invalid email or password");
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @Override
    @Transactional
    public UserResponseDto register(UserRequestDto request) {

        if(Objects.isNull(request)) {
            LOGGER.error("stage=error method=UserServiceImpl.register message=User data cannot be null");
            throw new RuntimeException("User data cannot be null");
        }

        LOGGER.info("stage=init method=UserServiceImpl.register dto={}", request);

        if(userRepository.existsByEmail(request.getEmail())) {
            LOGGER.error("stage=error method=UserServiceImpl.register message=User already exists");
            throw new AlreadyExistsException("User already exists");
        }

        var user = userEntityConverter.apply(request);

        userRepository.save(user);

        roleService.addRoleToUser(user.getId(), "BASIC");

        var response = userResponseConverter.apply(user);

        LOGGER.info("stage=end method=UserServiceImpl.register userName={}", response.getName());
        return response;
    }

    @Override
    public Page<UserResponseDto> getAllPagination(Integer page, Integer pageSize) {

        LOGGER.info("stage=init method=UserServiceImpl.getAllPagination");

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<User> usersPage = userRepository.findAll(pageable);

        var usersPageable = usersPage.map(userResponseConverter);

        LOGGER.info("stage=end method=UserServiceImpl.getAllPagination message=All users fetched");

        return usersPageable;
    }

    @Override
    public List<UserResponseDto> getAll() {

        LOGGER.info("stage=init method=UserServiceImpl.getAll");

        List<User> usersList = userRepository.findAll();

        var users = usersList.stream().map(userResponseConverter).toList();
        LOGGER.info("stage=end method=UserServiceImpl.getAll message=All users fetched");

        return users;
    }

    @Override
    public UserResponseDto getById(UUID id) {

        LOGGER.info("stage=init method=UserServiceImpl.getById userId={}", id);

        User user = userRepository.findById(id).orElseGet(() -> {
            LOGGER.error("stage=error method=UserServiceImpl.getById message=User not found");
            throw new NotFoundException("User not found");
        });

        var response = userResponseConverter.apply(user);

        LOGGER.info("stage=end method=UserServiceImpl.getById message=User fetched userName={}", response.getName());
        return response;
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {

        LOGGER.info("stage=init method=UserServiceImpl.deleteById userId={}", id);

        if(!userRepository.existsById(id)) {
            LOGGER.error("stage=error method=UserServiceImpl.deleteById message=User not found");
            throw new NotFoundException("User not found");
        }

        userRepository.deleteById(id);
        LOGGER.info("stage=end method=UserServiceImpl.deleteById message=User deleted");
    }
}
