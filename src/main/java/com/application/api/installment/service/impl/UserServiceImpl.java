package com.application.api.installment.service.impl;

import com.application.api.installment.converter.UserEntityConverter;
import com.application.api.installment.converter.UserPaginationResponseConverter;
import com.application.api.installment.converter.UserResponseConverter;
import com.application.api.installment.dto.PaginationResponseDto;
import com.application.api.installment.dto.UserRequestDto;
import com.application.api.installment.dto.UserResponseDto;
import com.application.api.installment.exception.AlreadyExistsException;
import com.application.api.installment.exception.NotFoundException;
import com.application.api.installment.exception.NotNullException;
import com.application.api.installment.model.User;
import com.application.api.installment.repository.UserRepository;
import com.application.api.installment.service.RoleService;
import com.application.api.installment.service.UserService;
import com.application.api.installment.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserEntityConverter userEntityConverter;
    private final UserResponseConverter userResponseConverter;
    private final PaginationUtils paginationUtils;
    private final UserPaginationResponseConverter userPaginationResponseConverter;


    @Override
    @Transactional
    public UserResponseDto register(UserRequestDto request) {

        if(Objects.isNull(request)) {
            LOGGER.error("stage=error method=UserServiceImpl.register message=User data cannot be null");
            throw new NotNullException("User data cannot be null");
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
    public PaginationResponseDto<UserResponseDto> getAllPagination(Integer page, Integer pageSize) {

        LOGGER.info("stage=init method=UserServiceImpl.getAllPagination");

        if(!paginationUtils.isAbleToPagination(page, pageSize)) {
            var usersList = getAll();
            LOGGER.info("stage=end method=UserServiceImpl.getAllPagination");
            return usersList;
        }

        Pageable pageable = paginationUtils.getPageable(page, pageSize);
        Page<User> usersPage = userRepository.findAllActivesUsersPagination(pageable);

        PaginationResponseDto<UserResponseDto> usersPageable = userPaginationResponseConverter.apply(usersPage);

        LOGGER.info("stage=end method=UserServiceImpl.getAllPagination message=All users fetched");

        return usersPageable;
    }

    @Override
    public UserResponseDto getById(String id) {

        LOGGER.info("stage=init method=UserServiceImpl.getById userId={}", id);

        User user = userRepository.findByUuid(id).orElseGet(() -> {
            LOGGER.error("stage=error method=UserServiceImpl.getById message=User not found");
            throw new NotFoundException("User not found");
        });;

        var response = userResponseConverter.apply(user);

        LOGGER.info("stage=end method=UserServiceImpl.getById message=User fetched userName={}", response.getName());
        return response;
    }

    @Override
    public UserResponseDto getActiveUserById(String id) {

        LOGGER.info("stage=init method=UserServiceImpl.getActiveUserById userId={}", id);

        User user = findActiveUserByUuid(id);
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
    public void deleteById(String id) {

        LOGGER.info("stage=init method=UserServiceImpl.deleteById userId={}", id);

        User user = findActiveUserByUuid(id);

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

    private PaginationResponseDto<UserResponseDto> getAll() {

        LOGGER.info("stage=init method=UserServiceImpl.getAll");

        List<User> usersList = userRepository.findAllActivesUsers();

        var users = usersList.stream().map(userResponseConverter).toList();
        LOGGER.info("stage=end method=UserServiceImpl.getAll message=All users fetched");

        return PaginationResponseDto.<UserResponseDto>builder()
                .content(users)
                .page(null)
                .build();
    }

    private User findActiveUserByUuid(String id) {

        LOGGER.error("stage=init method=UserServiceImpl.findActiveUserById id={}", id);

        User user = userRepository.findActiveUserByUuid(id).orElseGet(() -> {
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
