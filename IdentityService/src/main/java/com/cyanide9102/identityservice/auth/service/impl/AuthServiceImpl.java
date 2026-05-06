package com.cyanide9102.identityservice.auth.service.impl;

import com.cyanide9102.identityservice.auth.dto.AuthResponse;
import com.cyanide9102.identityservice.auth.dto.LoginRequest;
import com.cyanide9102.identityservice.auth.dto.RegisterRequest;
import com.cyanide9102.identityservice.auth.service.AuthService;
import com.cyanide9102.identityservice.common.exception.AuthBusinessException;
import com.cyanide9102.identityservice.common.exception.UnauthorizedException;
import com.cyanide9102.identityservice.role.Role;
import com.cyanide9102.identityservice.role.RoleRepository;
import com.cyanide9102.identityservice.security.JwtService;
import com.cyanide9102.identityservice.user.User;
import com.cyanide9102.identityservice.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AuthBusinessException("Username is already taken!");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthBusinessException("Email is already in use!");
        }

        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Default role not found!"));

        User user = User.builder().username(request.getUsername()).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).roles(Set.of(userRole)).build();
        user = userRepository.save(user);

        String token = jwtService.generateToken(user);
        return AuthResponse.builder().accessToken(token).build();
    }

    @Transactional(readOnly = true)
    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UnauthorizedException("Invalid username or password!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid username or password!");
        }

        String token = jwtService.generateToken(user);
        return AuthResponse.builder().accessToken(token).build();
    }
}
