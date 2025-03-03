package com.application.api.installment.repositories;

import com.application.api.installment.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u from User u JOIN FETCH u.userRoles ur JOIN FETCH ur.role WHERE u.email = :email")
    Optional<User> findByEmailWithRoles(@Param("email") String email);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
