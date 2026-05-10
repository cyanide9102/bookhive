package com.cyanide9102.common.security;

import com.cyanide9102.common.context.UserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@Order(1)
public class UserContextFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws ServletException, IOException {

        String userId = request.getHeader("X-User-Id");
        String userRoles = request.getHeader("X-User-Roles");

        if (userId != null) {
            UserContext.setUserId(userId);
        }

        if (userRoles != null) {
            UserContext.setUserRoles(Arrays.asList(userRoles.split(",")));
        }

        try {
            chain.doFilter(request, response);
        } finally {
            UserContext.clear();
        }
    }
}
