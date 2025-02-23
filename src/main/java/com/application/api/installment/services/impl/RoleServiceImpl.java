package com.application.api.installment.services.impl;

import com.application.api.installment.controllers.dto.RoleRequestDTO;
import com.application.api.installment.controllers.dto.RoleResponseDTO;
import com.application.api.installment.entities.Role;
import com.application.api.installment.entities.User;
import com.application.api.installment.entities.UserRole;
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
    public RoleResponseDTO createRole(RoleRequestDTO request) {
        if(roleRepository.existsByName(request.name().toUpperCase())) {
            throw new RuntimeException("Role already exists");
        }
        Role role = new Role();
        role.setName(request.name().toUpperCase());
        roleRepository.save(role);
        return new RoleResponseDTO(role.getId(), role.getName());
    }

    @Override
    public List<RoleResponseDTO> getAllRoles() {
        List<Role> roleList = roleRepository.findAll();
        return roleList
                .stream()
                .map(role -> new RoleResponseDTO(role.getId(), role.getName()))
                .toList();
    }

    @Override
    @Transactional
    public void addRoleToUser(UUID id, String roleName) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findByName(roleName.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRoleRepository.save(userRole);
    }
}
