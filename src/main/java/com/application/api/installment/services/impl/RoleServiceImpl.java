package com.application.api.installment.services.impl;

import com.application.api.installment.dto.RoleRequestDto;
import com.application.api.installment.dto.RoleResponseDto;
import com.application.api.installment.entities.Role;
import com.application.api.installment.entities.User;
import com.application.api.installment.entities.UserRole;
import com.application.api.installment.exceptions.AlreadyExistsException;
import com.application.api.installment.exceptions.NotFoundException;
import com.application.api.installment.repositories.RoleRepository;
import com.application.api.installment.repositories.UserRepository;
import com.application.api.installment.repositories.UserRoleRepository;
import com.application.api.installment.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    @Transactional
    public RoleResponseDto createRole(RoleRequestDto request) {
        if(roleRepository.existsByName(request.name().toUpperCase())) {
            throw new AlreadyExistsException("Role já existe");
        }
        Role role = new Role();
        role.setName(request.name().toUpperCase());
        roleRepository.save(role);
        return new RoleResponseDto(role.getId(), role.getName());
    }

    @Override
    public List<RoleResponseDto> getAllRoles() {
        List<Role> roleList = roleRepository.findAll();
        return roleList
                .stream()
                .map(role -> new RoleResponseDto(role.getId(), role.getName()))
                .toList();
    }

    @Override
    @Transactional
    public void addRoleToUser(UUID id, String roleName) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        Role role = roleRepository.findByName(roleName.toUpperCase())
                .orElseThrow(() -> new NotFoundException("Role não encontrada"));

        if(user.getUserRoles().stream().anyMatch(r -> r.getRole() == role)) {
            throw new AlreadyExistsException("Usuário já possui essa Role");
        }

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRoleRepository.save(userRole);
    }
}
