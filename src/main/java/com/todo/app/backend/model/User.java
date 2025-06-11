package com.todo.app.backend.model;

import com.todo.app.backend.model.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Document
public class User implements UserDetails {

    public User() {
        this.id = UUID.randomUUID().toString();
    }

    @Id
    private String id;

    @Field
    private String name;

    @Field
    private String surname;

    @Field
    private String email;

    @Field
    private String username;

    @Field
    private String password;

    @Field
    private List<UserRole> roles;

    @Field
    @CreatedDate
    private LocalDateTime createdAt;

    @Field
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(String.format("ROLE_%s", role.name())))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addRole(UserRole userRole) {
        if (this.roles == null) {
            this.roles = new ArrayList<>();
        }
        this.roles.add(userRole);
    }
}