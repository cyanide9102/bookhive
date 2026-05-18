package com.cyanide9102.identityservice.security;

import com.cyanide9102.identityservice.role.Role;
import com.cyanide9102.identityservice.user.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration}")
    private Long expiration;

    private SecretKey key;

    @PostConstruct
    public void init() {

        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(User user) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.subject(user.getId());
        jwtBuilder.issuedAt(now);
        jwtBuilder.expiration(expiryDate);
        jwtBuilder.claim("username", user.getUsername());
        jwtBuilder.claim("email", user.getEmail());
        jwtBuilder.claim("roles", user.getRoles().stream().map(Role::getName).toList());
        jwtBuilder.signWith(key);
        return jwtBuilder.compact();
    }
}
