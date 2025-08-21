package com.example.taskflow.service;

import com.example.taskflow.dto.AuthResponse;
import com.example.taskflow.dto.LoginRequest;
import com.example.taskflow.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
