package com.application.api.installment.service.impl;

import com.application.api.installment.converter.RoleEntityConverter;
import com.application.api.installment.converter.RoleResponseConverter;
import com.application.api.installment.dto.RoleRequestDto;
import com.application.api.installment.dto.RoleResponseDto;
import com.application.api.installment.exception.NotNullException;
import com.application.api.installment.model.Role;
import com.application.api.installment.model.User;
import com.application.api.installment.model.UserRole;
import com.application.api.installment.exception.AlreadyExistsException;
import com.application.api.installment.exception.NotFoundException;
import com.application.api.installment.repository.RoleRepository;
import com.application.api.installment.repository.UserRepository;
import com.application.api.installment.repository.UserRoleRepository;
import com.application.api.installment.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleEntityConverter roleEntityConverter;
    private final RoleResponseConverter roleResponseConverter;

    @Override
    @Transactional
    public RoleResponseDto createRole(RoleRequestDto request) {

        if(Objects.isNull(request)) {
            LOGGER.error("method=RoleServiceImpl.createRole message=Role data cannot be null");
            throw new NotNullException("Role data cannot be null");
        }

        LOGGER.info("stage=init method=RoleServiceImpl.createRole message=Role created dto={}", request);

        if(roleRepository.existsByName(request.getName().toUpperCase())) {
            LOGGER.error("stage=error method=RoleServiceImpl.createRole message=Role already exists");
            throw new AlreadyExistsException("Role already exists");
        }

        Role role = roleEntityConverter.apply(request);
        roleRepository.save(role);

        RoleResponseDto response = roleResponseConverter.apply(role);

        LOGGER.info("stage=end method=RoleServiceImpl.createRole response={}", response);

        return response;
    }

    @Override
    public List<RoleResponseDto> getAllRoles() {

        LOGGER.info("stage=init method=RoleServiceImpl.getAllRoles");
        List<Role> roleList = roleRepository.findAll();

        var roles = roleList.stream()
                .map(roleResponseConverter)
                .toList();

        LOGGER.info("stage=end method=RoleServiceImpl.getAllRoles message=Roles fetched");
        return roles;
    }

    @Override
    public RoleResponseDto getById(Long id) {

        LOGGER.info("stage=init method=RoleServiceImpl.getById roleId={}", id);

        Role role = roleRepository.findById(id).orElseGet(() -> {
            LOGGER.error("method=RoleServiceImpl.getById message=Role not found");
            throw new NotFoundException("Role not found");
        });

        var response = roleResponseConverter.apply(role);
        LOGGER.info("stage=end method=RoleServiceImpl.getById role={}", response);

        return response;
    }

    @Override
    @Transactional
    public void addRoleToUser(Long id, String roleName) {

        LOGGER.info("stage=init method=RoleServiceImpl.addRoleToUser roleId={} roleName={}",
                id, roleName);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        Role role = findByName(roleName);

        if(user.getUserRoles().stream().anyMatch(r -> r.getRole() == role)) {
            LOGGER.error("stage=error method=RoleServiceImpl.addRoleToUser message=User already has this role");
            throw new AlreadyExistsException("User already has this role");
        }

        var userRole = UserRole.builder()
                .user(user)
                .role(role)
                .build();

        userRoleRepository.save(userRole);

        LOGGER.info("stage=end method=RoleServiceImpl.addRoleToUser message=Role added to User roleId={} roleName={} userId={} userName={}",
                role.getId(), role.getName(), user.getId(), user.getName());
    }

    private Role findByName(String roleName) {

        if(Objects.isNull(roleName)) {
            LOGGER.error("stage=error method=RoleServiceImpl.findByName message=roleName cannot be null");
            throw new NotNullException("roleName cannot be null");
        }

        LOGGER.error("stage=init method=RoleServiceImpl.findByName roleName={}", roleName);

        Role role = roleRepository.findByName(roleName.toUpperCase())
                .orElseGet(() -> {
                    LOGGER.error("method=RoleServiceImpl.findByName message=Role not found");
                    throw new NotFoundException("Role not found");
                });

        LOGGER.error("stage=end method=RoleServiceImpl.findByName message=Role fetched roleId={}", role.getId());
        return role;
    }
}
