package com.todo.app.backend.model;

import com.todo.app.backend.model.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Document
@NoArgsConstructor
public class UserDetail extends BaseDocument implements UserDetails {

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