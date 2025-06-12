package com.todo.app.backend.service;

import com.todo.app.backend.dto.LoginRequest;
import com.todo.app.backend.dto.RegisterRequest;

public interface AuthService {

    String authenticateUser(LoginRequest loginRequest);

    String registerUser(RegisterRequest registerRequest);

}
