package com.application.api.installment.service.impl;

import com.application.api.installment.converter.UserEntityConverter;
import com.application.api.installment.converter.UserResponseConverter;
import com.application.api.installment.dto.UserRequestDto;
import com.application.api.installment.dto.UserResponseDto;
import com.application.api.installment.exception.AlreadyExistsException;
import com.application.api.installment.exception.NotFoundException;
import com.application.api.installment.model.User;
import com.application.api.installment.repository.UserRepository;
import com.application.api.installment.service.RoleService;
import com.application.api.installment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserEntityConverter userEntityConverter;
    private final UserResponseConverter userResponseConverter;


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

        Pageable pageable = PageRequest.of((page - 1), pageSize);
        Page<User> usersPage = userRepository.findAllActivesUsersPagination(pageable);

        var usersPageable = usersPage.map(userResponseConverter);

        LOGGER.info("stage=end method=UserServiceImpl.getAllPagination message=All users fetched");

        return usersPageable;
    }

    @Override
    public List<UserResponseDto> getAll() {

        LOGGER.info("stage=init method=UserServiceImpl.getAll");

        List<User> usersList = userRepository.findAllActivesUsers();

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
        });;

        var response = userResponseConverter.apply(user);

        LOGGER.info("stage=end method=UserServiceImpl.getById message=User fetched userName={}", response.getName());
        return response;
    }

    @Override
    public UserResponseDto getActiveUserById(UUID id) {

        LOGGER.info("stage=init method=UserServiceImpl.getActiveUserById userId={}", id);

        User user = findActiveUserById(id);
        var response = userResponseConverter.apply(user);

        LOGGER.info("stage=end method=UserServiceImpl.getActiveUserById message=User fetched userName={}", response.getName());
        return response;
    }

    @Override
    public UserResponseDto getActiveUserByEmail(String email) {
        LOGGER.info("stage=init method=UserServiceImpl.getActiveUserByEmail email={}", email);

        User user = findActiveUserByEmail(email);
        var response = userResponseConverter.apply(user);

        LOGGER.info("stage=end method=UserServiceImpl.getActiveUserByEmail message=User fetched userName={}", response.getName());
        return response;
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {

        LOGGER.info("stage=init method=UserServiceImpl.deleteById userId={}", id);

        User user = findActiveUserById(id);

        inactiveOrActiveUser(user);
        LOGGER.info("stage=end method=UserServiceImpl.deleteById message=User deleted");
    }

    @Override
    @Transactional
    public void deleteByEmail(String email) {

        LOGGER.info("stage=init method=UserServiceImpl.deleteByEmail email={}", email);

        User user = findActiveUserByEmail(email);

        inactiveOrActiveUser(user);
        LOGGER.info("stage=end method=UserServiceImpl.deleteByEmail message=User deleted");
    }

    @Override
    @Transactional
    public void activeByEmail(String email) {

        LOGGER.info("stage=init method=UserServiceImpl.activeByEmail email={}", email);

        User user = userRepository.findInactiveUserByEmail(email).orElseGet(() -> {
            LOGGER.error("stage=error method=UserServiceImpl.activeByEmail message=User not found");
            throw new NotFoundException("User not found");
        });;

        inactiveOrActiveUser(user);
        LOGGER.info("stage=end method=UserServiceImpl.activeByEmail message=User activated");
    }

    private User findActiveUserById(UUID id) {

        LOGGER.error("stage=init method=UserServiceImpl.findActiveUserById id={}", id);

        User user = userRepository.findActiveUserById(id).orElseGet(() -> {
            LOGGER.error("stage=error method=UserServiceImpl.findActiveUserById message=User not found");
            throw new NotFoundException("User not found");
        });

        LOGGER.error("stage=end method=UserServiceImpl.findActiveUserById userName={}", user.getName());
        return user;
    }

    private User findActiveUserByEmail(String email) {

        LOGGER.error("stage=init method=UserServiceImpl.findActiveUserByEmail email={}", email);

        User user = userRepository.findActiveUserByEmail(email).orElseGet(() -> {
            LOGGER.error("stage=error method=UserServiceImpl.findActiveUserByEmail message=User not found");
            throw new NotFoundException("User not found");
        });

        LOGGER.error("stage=end method=UserServiceImpl.findActiveUserByEmail userName={}", user.getName());
        return user;
    }

    private void inactiveOrActiveUser(User user) {
        user.setActive(!user.isActive());
        userRepository.save(user);
    }
}
