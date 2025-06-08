package com.application.api.installment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@ToString(exclude = {"expenses", "revenues", "userRoles"})
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
public class User extends BaseModel implements UserDetails {

    @Column(unique = true)
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Expense> expenses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Revenue> revenues;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserRole> userRoles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getName()))
                .toList();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public User() {
        this.uuid = "USER-" + UUID.randomUUID();
    }
}