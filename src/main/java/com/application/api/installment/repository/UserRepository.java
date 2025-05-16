package com.application.api.installment.repository;

import com.application.api.installment.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u from User u JOIN FETCH u.userRoles ur JOIN FETCH ur.role WHERE u.email = :email")
    Optional<User> findByEmailWithRoles(@Param("email") String email);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u from User u WHERE u.active = true")
    Page<User> findAllActivesUsersPagination(Pageable pageable);

    @Query("SELECT u from User u WHERE u.active = true")
    List<User> findAllActivesUsers();

    @Query("SELECT u from User u WHERE u.id = :id AND u.active = true")
    Optional<User> findActiveUserById(@Param("id") UUID id);

    @Query("SELECT u from User u WHERE u.email = :email AND u.active = true")
    Optional<User> findActiveUserByEmail(@Param("email") String email);

    @Query("SELECT u from User u WHERE u.email = :email AND u.active = false")
    Optional<User> findInactiveUserByEmail(@Param("email") String email);

}
