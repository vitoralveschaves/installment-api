package com.application.api.installment.configuration;

import com.application.api.installment.model.Role;
import com.application.api.installment.model.User;
import com.application.api.installment.model.UserRole;
import com.application.api.installment.repository.RoleRepository;
import com.application.api.installment.repository.UserRepository;
import com.application.api.installment.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class AdminUserConfiguration implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${password.user.admin}")
    private String adminPassword;

    @Value("${email.user.admin}")
    private String adminEmail;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserConfiguration.class);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var userAdmin = userRepository.findByEmail(adminEmail);
        var roleAdmin = roleRepository.findByName("ADMIN");

        userAdmin.ifPresentOrElse(
                user -> {
                    LOGGER.info("method=AdminUserConfiguration message=User admin already exists");
                }, () -> {
                    User user = new User();
                    user.setName("admin");
                    user.setEmail(adminEmail);
                    user.setPassword(passwordEncoder.encode(adminPassword));
                    user.setActive(Boolean.TRUE);
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
