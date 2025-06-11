package com.todo.app.backend.service.impl;

import com.todo.app.backend.dto.LoginRequest;
import com.todo.app.backend.dto.RegisterRequest;
import com.todo.app.backend.exception.UserAlreadyExistsException;
import com.todo.app.backend.model.UserDetail;
import com.todo.app.backend.model.enums.UserRole;
import com.todo.app.backend.repository.UserRepository;
import com.todo.app.backend.security.JwtTokenProvider;
import com.todo.app.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Override
    public String authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateToken(authentication);
    }

    @Override
    public String registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email is already in use!");
        }

        UserDetail userDetail = new UserDetail();
        userDetail.setName(registerRequest.getName());
        userDetail.setSurname(registerRequest.getSurname());
        userDetail.setUsername(registerRequest.getUsername());
        userDetail.setEmail(registerRequest.getEmail());
        userDetail.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userDetail.addRole(UserRole.USER); // Default role.

        UserDetail savedUserDetail = userRepository.save(userDetail);

        return String.format("User registered successfully with ID: %s", savedUserDetail.getId());
    }
}
