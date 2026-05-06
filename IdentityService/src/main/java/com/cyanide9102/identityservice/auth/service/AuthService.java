package com.cyanide9102.identityservice.auth.service;

import com.cyanide9102.identityservice.auth.dto.AuthResponse;
import com.cyanide9102.identityservice.auth.dto.LoginRequest;
import com.cyanide9102.identityservice.auth.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
