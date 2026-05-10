package com.cyanide9102.common.security;

import com.cyanide9102.common.context.RequestContext;
import com.cyanide9102.common.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class SecurityAspect {

    private final RequestContext requestContext;

    @Before("@annotation(com.cyanide9102.common.annotation.RequiresLogin) || @within(com.cyanide9102.common.annotation.RequiresLogin)")
    public void checkLogin() {

        if (requestContext.userId() == null || requestContext.userId().isBlank()) {
            throw new UnauthorizedException("You must be logged in to perform this action!");
        }
    }

    @Before("@annotation(com.cyanide9102.common.annotation.RequiresAdmin) || @within(com.cyanide9102.common.annotation.RequiresAdmin)")
    public void checkAdmin() {

        checkLogin();

        if (requestContext.roles() == null || !requestContext.roles().contains("ROLE_ADMIN")) {
            throw new UnauthorizedException("Administrator access required!");
        }
    }
}
