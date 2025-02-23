package com.application.api.installment.config;

import com.application.api.installment.entities.Role;
import com.application.api.installment.entities.User;
import com.application.api.installment.entities.UserRole;
import com.application.api.installment.repositories.RoleRepository;
import com.application.api.installment.repositories.UserRepository;
import com.application.api.installment.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class AdminUserConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${password.user.admin}")
    private String adminPassword;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var userAdmin = userRepository.findByEmail("admin@example.com");
        var roleAdmin = roleRepository.findByName("ADMIN");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("User admin already exists");
                }, () -> {
                    User user = new User();
                    user.setName("admin");
                    user.setEmail("admin@example.com");
                    user.setPassword(passwordEncoder.encode(adminPassword));
                    user.setActive(true);
                    userRepository.save(user);

                    UserRole userRole = new UserRole();
                    userRole.setUser(user);

                    roleAdmin.ifPresentOrElse(userRole::setRole, () -> {
                        Role role = new Role();
                        role.setName("ADMIN");
                        roleRepository.save(role);
                        userRole.setRole(role);
                    });
                    userRoleRepository.save(userRole);
                }
        );
    }
}
